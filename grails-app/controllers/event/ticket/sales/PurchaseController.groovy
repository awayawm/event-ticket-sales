package event.ticket.sales

import com.braintreegateway.ClientTokenRequest
import com.braintreegateway.Result
import com.braintreegateway.Transaction
import com.braintreegateway.TransactionRequest
import com.braintreegateway.exceptions.AuthenticationException
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.security.access.annotation.Secured

import java.math.RoundingMode

@Secured('permitAll')
class PurchaseController {
    EventService eventService = new EventService()
    ConfigService configService = new ConfigService()
    BraintreeService braintreeService = new BraintreeService()
    TicketService ticketService = new TicketService()
    PdfService pdfService = new PdfService()
    MailService mailService = new MailService()

    def index(){
        render view:"selectEvent", model:[events:eventService.getEvents().events]
    }
    def shortURL(){
        if (params.id) {
            def event = Event.findByShortURL(params.id)
            session.event_name = event.name
            session.doorsOpen = event.doorsOpen
            render model:[event:event], view:"selectTickets"
        }
    }

    def confirmation(){
        def itemMapList = [] // [[ticket-object:quantity]]
        def itemMap = [:]
        double total = 0
        int quantity = 0
        def token
        def numTicketsSold = 0
        def taxes = 0.0
        def config

        params.each(){ k,v ->
            if(k.startsWith("ticket_")){
                def ticketKey = k.substring(k.indexOf("_") + 1, k.length())
                def ticket = Ticket.findById(ticketKey)
                itemMap.ticketObject = ticket
                itemMap.quantity = v
                itemMapList << itemMap
                quantity = itemMap.quantity instanceof Integer ? itemMap.quantity : Double.parseDouble(itemMap.quantity)
                total += quantity * ticket.price
                numTicketsSold += quantity
                itemMap = [:]
            }
        }
        try{
            token = braintreeService.getClientToken()
        } catch(AuthenticationException ex){
            flash.message = "Could not get a Braintree clientKey, please check your configuration and/or environmental variable :("
            flash.class = "alert-danger"
            redirect action:'index'
        }

        session.totalBeforeFeesAndTaxes = new BigDecimal(total).setScale(2, RoundingMode.CEILING)
        session.totalSurcharge = new BigDecimal((double)configService.getConfig().admin.ticket_surcharge * numTicketsSold).setScale(2, RoundingMode.CEILING)
        session.taxes = new BigDecimal(session.totalBeforeFeesAndTaxes * (double)configService.getConfig().admin.tax_rate).setScale(2, RoundingMode.CEILING)
        session.totalAfterFeesAndTaxes = session.totalBeforeFeesAndTaxes + session.totalSurcharge + session.taxes
        session.itemMapList = itemMapList

        config = ["coordinator_email" : configService.getConfig().admin.coordinator_email,
                    "coordinator_phone_number": configService.getConfig().admin.coordinator_phone_number]

        def model = [config:config, itemMapList: itemMapList, clientToken:token]
        render view:"confirmation", model:model
    }

        def processPayment(){
        if(params.nonce) {
            TransactionRequest request = new TransactionRequest()
                    .amount(session.totalAfterFeesAndTaxes)
                    .paymentMethodNonce(params.nonce)
                    .customer()
                        .firstName(params.first_name)
                        .lastName(params.last_name)
                        .email(params.email_address)
                        .phone(params.phone_number)
                        .done()
                    .options()
                        .submitForSettlement(true)
                        .storeInVaultOnSuccess(true)
                        .done()

            Result<Transaction> result = braintreeService.getGateway().transaction().sale(request)

            if(result.isSuccess()){

                def uuid = RandomStringUtils.random(32, true, true)
                def saleStatus = new SaleStatus(transactionId: result.getTarget().getId(),
                                                status: "Submitted")
                Event event = Event.findByName(session.event_name)

                def allocConfig = configService.getConfig().allocation

                Double primaryAllocation = allocConfig.allocationEnabled ?
                        allocConfig.primaryPercentage * session.totalBeforeFeesAndTaxes + session.totalSurcharge + session.taxes
                        : session.totalBeforeFeesAndTaxes + session.totalSurcharge + session.taxes
                Double secondaryAllocation = allocConfig.allocationEnabled ?
                        (1 - allocConfig.primaryPercentage) * session.totalBeforeFeesAndTaxes
                        : 0

                def sale = new Sale(event:event, salesStatus: saleStatus, uuid: uuid,
                        rawRecord: ticketService.createRawRecord(session.itemMapList), totalBeforeFeesAndTaxes: session.totalBeforeFeesAndTaxes,
                        totalAfterFeesAndTaxes: session.totalAfterFeesAndTaxes, taxes: session.taxes, totalSurcharge: session.totalSurcharge,
                        primaryAllocation: primaryAllocation, secondaryAllocation: secondaryAllocation,
                        primaryPercentage: allocConfig.primaryPercentage, allocationEnabled: allocConfig.allocationEnabled,
                        customerName: "${params.first_name} ${params.last_name}", phoneNumber: "${params.phone_number}", emailAddress: "${params.email_address}",
                        ticketPDF: new byte[0])
                log.info "rendering ticket and adding to sale"
                sale.ticketPDF = pdfService.createTicketPdf(sale).toByteArray()
                log.info "saving sale to database"
                sale.save(failOnError:true)

                if(!sale){
                    // TODO send email on database save failures
                    return render(contentType:"application/json") {
                        success(false)
                        message("Could not save sale to database :(")
                    }
                } else {
                    return render(contentType:"application/json") {
                        log.info "attempting to mail ticket ..."
                        mailService.sendTicketPdf(sale)
                        ticketService.subtractItemMap(session.itemMapList)

                        session.sale = sale
                        success(true)
                        id(session.sale.uuid)
                    }
                }
            }
            return render(contentType:"application/json") {
                success(false)
                message(result.getMessage())
            }
        }
    }

    def getClientToken(){
        return braintreeService.getGateway().clientToken().generate(new ClientTokenRequest())
    }
}

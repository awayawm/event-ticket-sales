package event.ticket.sales

import com.braintreegateway.ClientTokenRequest
import com.braintreegateway.Result
import com.braintreegateway.Transaction
import com.braintreegateway.TransactionRequest
import com.braintreegateway.exceptions.AuthenticationException
import grails.converters.JSON

import java.math.RoundingMode
import java.time.format.DateTimeFormatter

class PurchaseController {
    EventService eventService = new EventService()
    ConfigService configService = new ConfigService()
    BraintreeService braintreeService = new BraintreeService()

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


        config = ["coordinator_email" : configService.getConfig().admin.coordinator_email,
                    "coordinator_phone_number": configService.getConfig().admin.coordinator_phone_number]

        def model = [config:config, itemMapList: itemMapList, clientToken:token]
        println itemMapList
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
                println result.getTarget().getId()
            }

            render(contentType:"application/json") {
                success(result.isSuccess())
                message(result.getMessage())
            }
        }
    }

    def getClientToken(){
        return braintreeService.getGateway().clientToken().generate(new ClientTokenRequest())
    }
}

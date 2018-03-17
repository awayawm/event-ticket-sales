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

        session.totalSurcharge = (double)configService.getConfig().admin.ticket_surcharge * numTicketsSold
        session.taxes = total * (double)configService.getConfig().admin.tax_rate
        session.totalBeforeFeesAndTaxes = total
        session.totalAfterFeesAndTaxes = total + session.totalSurcharge + session.taxes

        def model = [itemMapList: itemMapList, clientToken:token]
        render view:"confirmation", model:model
    }

    def processPayment(){
        if(params.nonce) {
            TransactionRequest request = new TransactionRequest()
                    .amount(new BigDecimal(session.totalAfterFeesAndTaxes).setScale(2, RoundingMode.CEILING))
                    .paymentMethodNonce(params.nonce)
                    .options()
                    .submitForSettlement(true)
                    .done()

            Result<Transaction> result = braintreeService.getGateway().transaction().sale(request)
            return render (success: result.isSuccess(), data: [message: result.getMessage()]) as JSON
        }
    }

    def getClientToken(){
        return braintreeService.getGateway().clientToken().generate(new ClientTokenRequest().customerId(params.id))
    }
}

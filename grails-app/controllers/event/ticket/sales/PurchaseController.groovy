package event.ticket.sales

import com.braintreegateway.ClientTokenRequest
import com.braintreegateway.Result
import com.braintreegateway.Transaction
import com.braintreegateway.TransactionRequest
import com.braintreegateway.exceptions.AuthenticationException
import grails.converters.JSON

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
            println event.doorsOpen
            render model:[event:event], view:"selectTickets"
        }
    }

    def confirmation(){
        def itemMapList = [] // [[ticket-object:quantity]]
        def itemMap = [:]
        def total = 0
        def quantity = 0
        def token
        params.each(){ k,v ->
            if(k.startsWith("ticket_")){
                def ticketKey = k.substring(k.indexOf("_") + 1, k.length())
                def ticket = Ticket.findById(ticketKey)
                itemMap.ticketObject = ticket
                itemMap.quantity = v
                itemMapList << itemMap

                quantity = itemMap.quantity instanceof Integer ? itemMap.quantity : Double.parseDouble(itemMap.quantity)
                total += quantity * ticket.price
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
        def model = [total:total, itemMapList: itemMapList, clientToken:token]
        render view:"confirmation", model:model
    }

    def processPayment(){
        println params
        if(params.nonce) {
            String nonceFromTheClient = params.nonce
            TransactionRequest request = new TransactionRequest()
                    .amount(new BigDecimal("10.00"))
                    .paymentMethodNonce(nonceFromTheClient)
                    .options()
                    .submitForSettlement(true)
                    .done()

            Result<Transaction> result = braintreeService.getGateway().transaction().sale(request)
            return [success: true, data: [result: result]] as JSON
        }
        [success: false] as JSON
    }

    def getClientToken(){
        return braintreeService.getGateway().clientToken().generate(new ClientTokenRequest().customerId(params.id))
    }
}

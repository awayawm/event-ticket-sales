package event.ticket.sales

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.Environment

class PurchaseController {
    EventService purchaseService = new EventService()
    ConfigService configService = new ConfigService()
    def index(){
        render view:"selectEvent", model:[events:purchaseService.getEvents().events]
    }
    def shortURL(){
        if (params.id) {
            def event = Event.findByShortURL(params.id)
            render model:[event:event], view:"selectTickets"
        }
    }

    def confirmation(){
        render view:"confirmation"
    }

    def processPayment(){
        println params
        BraintreeGateway gateway = new BraintreeGateway(
                Environment.SANDBOX,
                configService.getConfig().keys.braintree.merchantId,
                configService.getConfig().keys.braintree.publicKey,
                configService.getConfig().keys.braintree.privateKey
        );
    }
}

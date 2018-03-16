package event.ticket.sales

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.ClientTokenRequest
import com.braintreegateway.Environment
import com.braintreegateway.Result
import com.braintreegateway.Transaction
import com.braintreegateway.TransactionRequest

class PurchaseController {
    EventService purchaseService = new EventService()
    ConfigService configService = new ConfigService()
    BraintreeService braintreeService = new BraintreeService()

    def index(){
        render view:"selectEvent", model:[events:purchaseService.getEvents().events, clientToken: braintreeService.getClientToken()]
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
        String nonceFromTheClient = request.queryParams("params.payment_method_nonce");
        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal("10.00"))
                .paymentMethodNonce(nonceFromTheClient)
                .options()
                .submitForSettlement(true)
                .done()

        Result<Transaction> result = gateway.transaction().sale(request);

        println result.toString()
    }

    def getClientToken(){
        return BraintreeService.getGateway().clientToken().generate(new ClientTokenRequest().customerId(params.id))
    }
}

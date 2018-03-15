package event.ticket.sales

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.Environment

class BraintreeService {
    BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            configService.getConfig().keys.braintree.merchantId,
            configService.getConfig().keys.braintree.publicKey,
            configService.getConfig().keys.braintree.privateKey
    )
    def getGateway(){
        return gateway
    }
}

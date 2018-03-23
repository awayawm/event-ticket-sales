package event.ticket.sales

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.Environment

class BraintreeService {

    ConfigService configService = new ConfigService()
    BraintreeGateway gateway

    def getGateway(){
        new BraintreeGateway(
                Environment.SANDBOX,
                configService.getConfig().keys.braintree.merchantId,
                configService.getConfig().keys.braintree.publicKey,
                configService.getConfig().keys.braintree.privateKey)
    }

    def getClientToken() {
        if (gateway == null){
            gateway = getGateway()
        }
        gateway.clientToken().generate()
    }

    def getTransactionStatus(String transactionId){

    }
}

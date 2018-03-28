package event.ticket.sales

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.Environment
import com.braintreegateway.Transaction

class BraintreeService {

    ConfigService configService = new ConfigService()
    BraintreeGateway gateway

    def getGateway(){
        if(gateway == null) {
            gateway = new BraintreeGateway(
                    Environment.SANDBOX,
                    configService.getConfig().keys.braintree.merchantId,
                    configService.getConfig().keys.braintree.publicKey,
                    configService.getConfig().keys.braintree.privateKey)
        }
        gateway
    }

    def getClientToken() {
        if (gateway == null){
            gateway = getGateway()
        }
        gateway.clientToken().generate()
    }

    Transaction getTransactionStatus(String transactionId){
        getGateway().transaction().find(transactionId)
    }
}

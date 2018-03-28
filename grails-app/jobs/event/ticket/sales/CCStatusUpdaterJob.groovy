package event.ticket.sales

import com.braintreegateway.Transaction

class CCStatusUpdaterJob {

    BraintreeService braintreeService = new BraintreeService()

    static triggers = {
        simple name: "CCStatus Updater", repeatInterval: 600000
    }

    def execute() {
        Sale.findAll().each(){
            Transaction status = braintreeService.getTransactionStatus(it.salesStatus.transactionId)
            if(status.getStatus().toString() != it.salesStatus.status) {
                log.info "${it.salesStatus.transactionId} status updated at ${status.getUpdatedAt().getTime().toLocaleString()}"
                it.salesStatus.status = status.getStatus().toString()
                it.save()
            }
        }
    }
}

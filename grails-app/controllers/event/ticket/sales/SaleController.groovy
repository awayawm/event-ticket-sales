package event.ticket.sales

class SaleController {
    TicketService ticketService = new TicketService()

    def index() {
        [sales:Sale.findAll([sort: "dateCreated", order: "desc"])]
    }

    def status(){
        def sale = Sale.findByUuid(params.id)
        if(sale) {
            render view:"status", model:[sale:sale, tickets:ticketService.rawRecordToRawRecordItemMap(sale.rawRecord)]
        } else {
            flash.message = "That sale UUID was not found :("
            flash.class = "alert alert-danger"
            redirect uri:"/"
        }
    }
}

package event.ticket.sales

class SaleController {
    TicketService ticketService = new TicketService()

    def index() {
        [sales:Sale.findAll([sort: "dateCreated", order: "desc"])]
    }

    def status(){
        if(params.id) {
            def sale = Sale.findByUuid(params.id)
            [sale:sale, tickets:ticketService.rawRecordToRawRecordItemMap(sale.rawRecord)]
        }
    }
}

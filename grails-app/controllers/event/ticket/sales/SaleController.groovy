package event.ticket.sales

import grails.plugin.springsecurity.annotation.Secured

class SaleController {
    TicketService ticketService = new TicketService()

    @Secured('ROLE_ADMIN')
    def index() {
        [sales:Sale.findAll([sort: "dateCreated", order: "desc"])]
    }

    @Secured('permitAll')
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

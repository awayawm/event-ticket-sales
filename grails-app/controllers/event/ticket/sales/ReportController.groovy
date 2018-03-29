package event.ticket.sales

import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
class ReportController {

    def index() { }
}

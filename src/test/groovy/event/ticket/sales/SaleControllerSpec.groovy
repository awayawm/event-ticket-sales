package event.ticket.sales

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class SaleControllerSpec extends Specification implements ControllerUnitTest<SaleController>, DataTest {

    def setup() {
        mockDomain Sale
    }

    def cleanup() {
        Sale.metaClass = null
        TicketService.metaClass = null
    }

    def "does sale/status forward to /event/index with error flash when uuid not found"(){
        when:
        controller.status()
        then:
        controller.flash.message == "That sale UUID was not found :("
        controller.flash.class == "alert alert-danger"
        response.redirectUrl == "/"
    }

    def "does sale/status model have things when uuid returns not null"(){
        setup:
        Sale.metaClass.static.findByUuid = { def uuid ->
            new Sale()
        }
        TicketService.metaClass.rawRecordToRawRecordItemMap = { def rawRecord ->
            []
        }
        params.id = 1
        when:
        controller.status()
        then:
        model.size() == 2
    }

}

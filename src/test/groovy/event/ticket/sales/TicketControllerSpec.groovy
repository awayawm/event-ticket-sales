package event.ticket.sales

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import org.apache.commons.io.IOUtils
import spock.lang.Specification

class TicketControllerSpec extends Specification implements ControllerUnitTest<TicketController>, DataTest {

    Class[] getDomainClassesToMock(){
        Ticket
    }

    byte[] ticketImage = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground.png"))
    byte[] ticketLogo = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo.gif"))

    def setup() {
    }

    def cleanup() {
    }

    void "error flash and redirect to /ticket/create when incomplete parameters on create()"(){
        setup:
        request.method = "POST"
        params.ticket_name = "General Admission"

        when:
        controller.create()

        then:
        controller.flash.message == "Not all parameters provided :("
        controller.flash.class == "error"
        response.redirectUrl == "/ticket/create"
    }

    void "success flash and redirect to /ticket/index when ticket saved by create()"(){
        setup:
        request.method = "POST"
        params.ticket_name = "General Admission"
        params.ticket_description = "Stadium seating"
        params.ticket_price = 30.00
        params.ticket_ticketImage = ticketImage
        params.ticket_ticketLogo = ticketLogo
        Ticket.count() == 0

        when:
        controller.create()

        then:
        controller.flash.message == "Ticket successfully created :)"
        controller.flash.class == "success"
        response.redirectUrl == "/ticket/create"
        Ticket.count() == 1
    }


}

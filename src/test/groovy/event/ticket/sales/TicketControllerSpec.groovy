package event.ticket.sales

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import org.apache.commons.io.IOUtils
import org.grails.plugins.testing.GrailsMockMultipartFile
import spock.lang.Specification

class TicketControllerSpec extends Specification implements ControllerUnitTest<TicketController>, DataTest {

    Class[] getDomainClassesToMock(){
        Ticket
    }

    byte[] ticketImage = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground.png"))
    byte[] ticketLogo = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo.gif"))
    byte[] ticketLogo2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo2.jpg"))

    def addTicket(){
        new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()

    }

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
        controller.flash.class == "alert-danger"
        response.redirectUrl == "/ticket/create"
    }

    void "success flash and redirect to /ticket/index when ticket saved by create()"(){
        setup:
        request.method = "POST"
        params.ticket_name = "General Admission"
        params.ticket_description = "Stadium seating"
        params.ticket_price = 30.00
        params.ticket_ticketImage = new GrailsMockMultipartFile('ticket_ticketImage', 'IronMan3.jpg', 'image/jpeg', "1234567" as byte[])
        params.ticket_ticketLogo = new GrailsMockMultipartFile('ticket_ticketLogo', 'IronMan3.jpg', 'image/jpeg', "1234567" as byte[])
        Ticket.count() == 0

        when:
        controller.create()

        then:
        controller.flash.message == "Ticket successfully created :)"
        controller.flash.class == "alert-success"
        response.redirectUrl == "/ticket/index"
        Ticket.count() == 1
    }

    void "does index return all tickets"(){
        setup:
        addTicket()
        addTicket()
        addTicket()

        when:
        def model = controller.index()

        then:
        model.tickets.size() == 3
    }

    void "when ticket removed is user redirected to index"(){
        setup:
        addTicket()
        Ticket.count() == 1
        params.id = Long.toString(Ticket.findAll()[0].id)

        when:
        controller.delete()

        then:
        Ticket.count() == 0
        response.redirectUrl == "/ticket/index"
        controller.flash.message == "Ticket has been deleted :)"
        controller.flash.class == "alert-success"
    }

    void "delete redirects to index and displays flash when no id parameter passed"(){
        when:
        controller.delete()

        then:
        controller.flash.message == "Cannot delete without an id :("
        controller.flash.class == "alert-danger"
        response.redirectUrl == "/ticket/index"

    }

    void "can saved ticket be updated"(){
        setup:
        addTicket()
        Ticket.count() == 1
        params.id = Ticket.findAll()[0].id
        request.method = "POST"
        params.ticket_name = "Cage Seats"
        params.ticket_description = "Tables that encircle the cage"
        params.ticket_ticketImage = new GrailsMockMultipartFile("test", new byte[0])
        params.ticket_ticketLogo = new GrailsMockMultipartFile("updated logo", "ticketLogo2.jpg", "image/jpg", ticketLogo2)
        params.ticket_price = Double.toString(Ticket.findAll()[0].price)

        when:
        controller.edit()

        then:
        Ticket.count() == 1
        Ticket.findAll()[0].name == "Cage Seats"
        Ticket.findAll()[0].ticketLogoBytes == ticketLogo2
        controller.flash.message == "ticket successfully updated :)"
        controller.flash.class == "alert-success"
        response.redirectUrl == '/ticket/index'

    }

}

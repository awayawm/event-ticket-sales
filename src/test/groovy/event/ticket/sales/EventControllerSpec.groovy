package event.ticket.sales

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import org.apache.commons.io.IOUtils
import spock.lang.Specification

class EventControllerSpec extends Specification implements ControllerUnitTest<EventController>, DataTest{

    byte[] ticketImage = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground.png"))
    byte[] ticketImage2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground2.jpg"))
    byte[] ticketLogo = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo.gif"))
    byte[] ticketLogo2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo2.jpg"))
    byte[] poster = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster.jpg"))
    byte[] poster2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster2.jpg"))
    byte[] poster3 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster3.jpg"))

    def createEvent(){
        def ticket = new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()

        new Event(name: "Battle on the Boat", shortURL: "battle-on-the-boat", description: "12 fights and 2 championship fights for a full night of entertainment!",
                address: "313 E. Scott, Kirksville MO, 63501", posterContentType: "image/jpg", posterBytes: poster, posterName: "poster.jpg",
                doorsOpen: new Date(2017, 12, 1, 19, 0), eventStarts: new Date(2017, 12, 1, 19, 0),
                stopTicketSalesAt: new Date(2017, 12, 1, 19, 0), enabled: true, tickets: ticket).save()
    }

    def setup() {
        mockDomain Ticket
        mockDomain Event
    }

    def cleanup() {
    }

    def "index returns all events"(){
        setup:
        createEvent()
        createEvent()
        when:
        def model = controller.index()
        then:
        model.events.size() == 2

    }
}

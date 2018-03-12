package event.ticket.sales

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import org.apache.commons.io.IOUtils
import org.grails.plugins.testing.GrailsMockMultipartFile
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

    def "can event be created"(){
        setup:
        def ticket = new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()
        Ticket.count() == 1

        request.method = "POST"
        params.name = "Fight at the Farm"
        params.shortURL = "fight-at-the-farm"
        params.description = "After the fight try the corn field maze,  Only \$10!"
        params.address = "123 Cool Lane"
        params.doorsOpen = "Sun Apr 07 13:30:00 CDT 3918"
        params.eventStarts = "Sun Apr 07 13:30:00 CDT 3918"
        params.stopTicketSalesAt = "Sun Apr 07 13:30:00 CDT 3918"
        params.enabled = "true"
        params.poster = new GrailsMockMultipartFile('poster.jpg', 'poster.jpg', 'image/jpeg', poster)
        params.tickets = ticket.id as String

        when:
        controller.create()

        then:
        Event.count() == 1
        Event.findAll()[0].tickets[0].name == "General Admission"
        response.redirectUrl == '/event/index'
        controller.flash.message == "Event saved successfully :)"
        controller.flash.class == "alert-success"
    }

    void "does event create view return list of all tickets as tickets"(){
        setup:
        new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()
        Ticket.count() == 1
        request.method = "GET"

        when:
        controller.create()

        then:
        model.tickets.size() == 1

    }
}

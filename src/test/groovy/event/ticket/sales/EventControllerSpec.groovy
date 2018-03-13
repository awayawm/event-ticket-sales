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

    def createTickets(){
        new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()
        new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()
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
        response.redirectUrl == '/admin/event'
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

    void "does edit return event and tickets when provided id"(){
        setup:
        createEvent()
        createTickets()
        params.id = 1
        when:
        controller.edit()
        then:
        model.event instanceof Event
        model.tickets.size() == 3
        view == "/event/edit"
    }

    void "does edit get redirect with no id"(){
        when:
        request.method = "GET"
        controller.edit()
        then:
        controller.flash.message == "Nothing to see here :("
        controller.flash.class == "alert-danger"
        response.redirectUrl == "/admin/event"
    }

    void "can event be changed"(){
        setup:
        createEvent()
        def ticket = new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()
        Ticket.count() == 2

        params.id = Event.findAll()[0]
        request.method = "POST"
        params.name = "renamed event"
        params.shortURL = "fight-at-the-farm"
        params.description = "After the fight try the corn field maze,  Only \$10!"
        params.address = "123 Cool Lane"
        params.doorsOpen = "Sun Apr 07 13:30:00 CDT 3918"
        params.eventStarts = "Sun Apr 07 13:30:00 CDT 3918"
        params.stopTicketSalesAt = "Sun Apr 07 13:30:00 CDT 3918"
        params.enabled = "false"
        params.poster = new GrailsMockMultipartFile('poster.jpg', 'poster.jpg', 'image/jpeg', new byte[0])
        params.tickets = [ticket.id as String]

        when:
        controller.edit()

        then:
        Event.findAll()[0].name == "renamed event"
        Event.findAll()[0].enabled == false
        Event.findAll()[0].posterBytes == poster
        controller.flash.message == "Event successfully edited :)"
        controller.flash.class == "alert-success"
        response.redirectUrl == "/admin/event"
        Event.findAll()[0].tickets[0].name == ticket.name
    }

    void "does image get overridden on edit"(){
        setup:
        createEvent()
        def ticket = new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()
        Ticket.count() == 2

        params.id = Event.findAll()[0]
        request.method = "POST"
        params.name = "renamed event"
        params.shortURL = "fight-at-the-farm"
        params.description = "After the fight try the corn field maze,  Only \$10!"
        params.address = "123 Cool Lane"
        params.doorsOpen = "Sun Apr 07 13:30:00 CDT 3918"
        params.eventStarts = "Sun Apr 07 13:30:00 CDT 3918"
        params.stopTicketSalesAt = "Sun Apr 07 13:30:00 CDT 3918"
        params.enabled = "false"
        params.poster = new GrailsMockMultipartFile('poster.jpg', 'poster.jpg', 'image/jpeg', poster2)
        params.tickets = [ticket.id as String]

        when:
        controller.edit()

        then:
        Event.findAll()[0].posterBytes == poster2
    }

}

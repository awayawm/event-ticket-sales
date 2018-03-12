package event.ticket.sales

import grails.testing.gorm.DataTest
import org.apache.commons.io.IOUtils
import spock.lang.Specification

class EventSpec extends Specification implements DataTest {

    byte[] ticketImage = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground.png"))
    byte[] ticketImage2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground2.jpg"))
    byte[] ticketLogo = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo.gif"))
    byte[] ticketLogo2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo2.jpg"))
    byte[] poster = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster.jpg"))
    byte[] poster2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster2.jpg"))
    byte[] poster3 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster3.jpg"))

    def setup() {
        mockDomain Ticket
        mockDomain Event
    }

    def createEvent(){
        def ticket = new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()

        new Event(name: "Battle on the Boat", shortURL: "battle-on-the-boat", description: "12 fights and 2 championship fights for a full night of entertainment!",
                address: "313 E. Scott, Kirksville MO, 63501", posterContentType: "image/jpg", posterBytes: poster, posterName: "poster.jpg",
                doorsOpen: new Date(2017, 12, 1, 19, 0), eventStarts: new Date(2017, 12, 1, 19, 0),
                stopTicketSalesAt: new Date(2017, 12, 1, 19, 0), enabled: true, tickets: [ticket]).save()
    }

    def cleanup() {
    }

    def "can event be saved"(){
        when:
        createEvent()

        then:
        Event.count() == 1
        Event.findAll()[0].tickets[0].name == "General Admission"
    }

    def "can event be deleted"(){
        when:
        createEvent()
        Event.findAll()[0].delete()

        then:
        Event.count() == 0
    }

    def "can tickets be updated"(){
        when:
        def ticket = new Ticket(name: "cage seat", description: "cage seat", price: "50.00",
                ticketImageName: "ticketBackground2.jpg", ticketImageBytes: ticketImage2, ticketImageContentType: "image/jpg",
                ticketLogoName: "ticketLogo2.jpg", ticketLogoContentType: "image/jpg", ticketLogoBytes: ticketLogo2).save()
        createEvent()
        Event.findAll()[0].addToTickets(ticket)

        then:
        Event.findAll()[0].tickets.size() == 2
        Event.findAll()[0].tickets.findAll() { it.name == "cage seat" || it.name == "General Admission" }
    }
}

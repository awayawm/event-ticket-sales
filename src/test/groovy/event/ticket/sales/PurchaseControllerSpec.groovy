package event.ticket.sales

import com.braintreegateway.BraintreeGateway
import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import org.apache.commons.io.IOUtils
import spock.lang.Specification

class PurchaseControllerSpec extends Specification implements ControllerUnitTest<PurchaseController>, DataTest {

    byte[] ticketImage = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground.png"))
    byte[] ticketImage2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground2.jpg"))
    byte[] ticketLogo = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo.gif"))
    byte[] ticketLogo2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo2.jpg"))
    byte[] poster = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster.jpg"))
    byte[] poster2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster2.jpg"))
    byte[] poster3 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster3.jpg"))

    def createEvent(){
        def ticket = new Ticket(quantity: 100, name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save(flush:true)

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
        controller.braintreeService.metaClass = null
        BraintreeGateway.metaClass = null
        ConfigService.metaClass = null
        BraintreeService.metaClass = null
    }

    def "does poster index get return all events"(){
        setup:
        createEvent()
        createEvent()
        createEvent()
        controller.braintreeService.metaClass.getClientToken = {}
        when:
        controller.index()
        then:
        model.events.size() == 3
    }

    def "do shorturls work?"(){
        setup:
        createEvent()
        params.id = "battle-on-the-boat"

        when:
        controller.shortURL()

        then:
        println model.event.name
    }

    def "does purchase confirmation return an event and a client token?"(){
        setup:
        createEvent()
        params.id = Event.findAll()[0].id
        controller.braintreeService.metaClass.getClientToken = {
            "client token"
        }

        ConfigService.metaClass.constructor = {}
        BraintreeService.metaClass.constructor = {}

        when:
        controller.confirmation()
        then:
        model.clientToken != null
        model.clientToken == "client token"

    }

    def "does confirmation parse tickets from request"(){
        setup:
        def ticket1 = new Ticket(quantity: 100, name: "Cage seat", description: "Stadium seating", price: "8.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save(flush:true)

        def ticket2 = new Ticket(quantity: 100, name: "Water seat", description: "view the event from an above ground pool.  Drinks sold separately.", price: "10.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save(flush:true)

        def ticket3 = new Ticket(quantity: 100, name: "Cage seat", description: "Stadium seating", price: "8.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save(flush:true)

        controller.braintreeService.metaClass.getClientToken = {
            "client token"
        }

        ConfigService.metaClass.constructor = {}
        BraintreeService.metaClass.constructor = {}

        params."ticket_1" = 0
        params."ticket_2" = 2
        params."ticket_3" = 1

        when:
        controller.confirmation()

        then:
        println model.itemMapList
        model.total == 28.0
        model.itemMapList.size() == 3
    }

}

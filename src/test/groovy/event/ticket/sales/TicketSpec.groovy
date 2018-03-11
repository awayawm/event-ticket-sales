package event.ticket.sales

import grails.testing.gorm.DomainUnitTest
import org.apache.commons.io.IOUtils
import org.grails.core.io.ResourceLocator
import spock.lang.Specification

class TicketSpec extends Specification implements DomainUnitTest<Ticket> {

    byte[] ticketImage = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground.png"))
    byte[] ticketLogo = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo.gif"))

    def setup() {
    }

    def cleanup() {
    }

    void "can ticket be saved"(){
        setup:
        new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()

        expect:
        Ticket.count() == 1
        Ticket.findAll()[0].ticketImageBytes == ticketImage
    }

    void "can saved ticket be updated"(){
        setup:
        def ticket = new Ticket(name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo).save()

        when:
        ticket.name = "Cage seat"
        ticket.save()

        then:
        Ticket.findAll()[0].name == "Cage seat"

    }
}

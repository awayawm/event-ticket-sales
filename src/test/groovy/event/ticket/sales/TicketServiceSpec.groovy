package event.ticket.sales

import grails.testing.gorm.DataTest
import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import org.apache.commons.io.IOUtils
import spock.lang.Specification

class TicketServiceSpec extends Specification implements ServiceUnitTest<TicketService>, DomainUnitTest<Ticket>{


    def resourcePath = "${System.properties['user.dir']}/src/test/resources"
    byte[] titleBackground = IOUtils.toByteArray(new FileInputStream("${resourcePath}/ticketBackground.png"))
    byte[] titleBackground2 = IOUtils.toByteArray(new FileInputStream("${resourcePath}/ticketBackground2.jpg"))
    byte[] ticketLogo = IOUtils.toByteArray(new FileInputStream("${resourcePath}/ticketLogo.gif"))
    byte[] ticketLogo2 = IOUtils.toByteArray(new FileInputStream("${resourcePath}/ticketLogo2.jpg"))

    def setup() {
        new Ticket(quantity: 75, name: "General Admission", description: "Stadium Seating", price: "30.00",
                ticketImageName: "titleBackground.png", ticketImageBytes: titleBackground, ticketImageContentType: "image/png",
                ticketLogoName: "ticketLogo.gif", ticketLogoContentType: "image/gif", ticketLogoBytes: ticketLogo).save()
        new Ticket(quantity: 60, name: "Cage Seating", description: "Tables around the cage", price: "60.00",
                ticketImageName: "titleBackground2.jpg", ticketImageBytes: titleBackground2, ticketImageContentType: "image/jpg",
                ticketLogoName: "ticketLogo2.jpg", ticketLogoContentType: "image/jpg", ticketLogoBytes: ticketLogo2).save()
        new Ticket(quantity: 9, name: "Table Seats", description: "Tables outside the ring", price: "45.00",
                ticketImageName: "titleBackground.png", ticketImageBytes: titleBackground, ticketImageContentType: "image/png",
                ticketLogoName: "ticketLogo2.jpg", ticketLogoContentType: "image/jpg", ticketLogoBytes: ticketLogo2).save()
        new Ticket(quantity: 30, name: "Director Seat", description: "View the fights from 50 feet in the air on the safety of a cherrypicker.  Waiver required.", price: "120.00",
                ticketImageName: "titleBackground2.jpg", ticketImageBytes: titleBackground2, ticketImageContentType: "image/jpg",
                ticketLogoName: "ticketLogo.gif", ticketLogoContentType: "image/gif", ticketLogoBytes: ticketLogo).save()
    }

    def cleanup() {
    }

    def "can ticket ticket quantities be reduced by the amount of an itemmap [ticket-object:quantity]"() {
        when:
        def itemMap = [[ticketObject:Ticket.findByName("General Admission"), quantity:4],
                       [ticketObject:Ticket.findByName("Cage Seating"),quantity:3],
                       [ticketObject:Ticket.findByName("Director Seat"),quantity:7]]
        service.subtractItemMap(itemMap)
        then:
        Ticket.findByName("General Admission").quantity == 71
        Ticket.findByName("Cage Seating").quantity == 57
        Ticket.findByName("Director Seat").quantity == 23
        Ticket.findByName("Table Seats").quantity == 9

    }

    def "can raw record be created from a itemmap [ticket-object:quantity]"(){
        when:
        def itemMap = [[ticketObject:Ticket.findByName("General Admission"), quantity:4],
                       [ticketObject:Ticket.findByName("Cage Seating"),quantity:3]]
        def rawRecord = service.createRawRecord(itemMap)
        then:
        rawRecord == "General Admission,Stadium Seating,30.0,4,Cage Seating,Tables around the cage,60.0,3"
    }

    def "can itemMap be created from rawRecord"(){
        when:
        def rawRecord = "General Admission,Stadium Seating,30.0,4,Cage Seating,Tables around the cage,60.0,3"
        def itemMap = service.rawRecordToRawRecordItemMap(rawRecord)
        then:
        itemMap == [[name:'General Admission', description: 'Stadium Seating', price: '30.0', quantity: '4'],
                    [name:'Cage Seating', description: 'Tables around the cage', price:'60.0', quantity: '3']]
    }

}

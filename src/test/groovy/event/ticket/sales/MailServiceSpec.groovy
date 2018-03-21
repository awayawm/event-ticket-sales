package event.ticket.sales

import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Specification

class MailServiceSpec extends Specification implements ServiceUnitTest<MailService>, DataTest{

    byte[] ticketImage = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground.png"))
    byte[] ticketImage2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketBackground2.jpg"))
    byte[] ticketLogo = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo.gif"))
    byte[] ticketLogo2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("ticketLogo2.jpg"))
    byte[] poster = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster.jpg"))
    byte[] poster2 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster2.jpg"))
    byte[] poster3 = IOUtils.toByteArray(this.class.classLoader.getResourceAsStream("poster3.jpg"))

    Ticket ticket
    Ticket ticket2
    Event event

    def createEvent(){
        DateService dateService = new DateService()
        ticket = new Ticket(quantity: 100, name: "General Admission", description: "Stadium seating", price: "30.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo)

        ticket2 = new Ticket(quantity: 80, name: "Underwater Seat", description: "Includes snorkle", price: "10.00",
                ticketImageName: "name", ticketImageBytes: ticketImage, ticketImageContentType: "image/png",
                ticketLogoName: "image", ticketLogoContentType: "image/png", ticketLogoBytes: ticketLogo)

        event = new Event(name: "Battle on the Boat", shortURL: "battle-on-the-boat", description: "12 fights and 2 championship fights for a full night of entertainment!",
                address: "313 E. Scott, Kirksville MO, 63501", posterContentType: "image/jpg", posterBytes: poster, posterName: "poster.jpg",
                doorsOpen: dateService.getDate(2017, 12, 1, 19, 0), eventStarts: dateService.getDate(2017, 12, 1, 19, 0),
                stopTicketSalesAt: dateService.getDate(2017, 12, 1, 19, 0), enabled: true, tickets: ticket)
    }

    def setup() {
        mockDomain Ticket
        mockDomain Sale
    }

    def cleanup() {
    }

    void "does sendTicketPdf send ticket email"() {
        setup:
        String emailAddress = "lakeoftea@gmail.com"
        createEvent()
        ticket.save()
        ticket2.save()
        def itemMap = [['ticketObject':ticket, 'quantity':4],
                       ['ticketObject':ticket2, 'quantity':3]]
        TicketService ticketService = new TicketService()
        def uuid = RandomStringUtils.random(32, true, true)
        def saleStatus = new SaleStatus(transactionId: "68kep29z", status: "Approved")
        Sale sale = new Sale(event:event, salesStatus: saleStatus, uuid: uuid,
                rawRecord: ticketService.createRawRecord(itemMap), totalBeforeFeesAndTaxes: 10.0,
                totalAfterFeesAndTaxes: 12.0, taxes: 0.80, totalSurcharge: 2.0, primaryAllocation: 0.0,
                secondaryAllocation: 0.0, primaryPercentage: 0.8, allocationEnabled: false,
                customerName: "dude", phoneNumber: "234", emailAddress: emailAddress,
                ticketPDF: new byte[0]).save(failOnError:true)
        ByteArrayOutputStream byteArrayOutputStream = new PdfService().createTicketPdf(sale)
        sale.ticketPDF = byteArrayOutputStream.toByteArray()
        sale.save(flush:true)

        FileUtils.writeByteArrayToFile(new File("filename.pdf"), sale.ticketPDF)
        when:
        service.sendTicketPdf(sale)
        then:
        false
    }
}

package event.ticket.sales

import grails.gorm.transactions.Transactional
import groovy.xml.MarkupBuilder

@Transactional
class SaleService {

    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)
    TicketService ticketService = new TicketService()

    def generateTicketXml(def sale) {
        xml.Sale(){
            Event(Name:sale.event.name, Description: sale.event.description,
                    DoorsOpen:sale.event.doorsOpen, EventStarts: sale.event.eventStarts,
                    Address:sale.event.address)
            Tickets {
                ticketService.rawRecordToRawRecordItemMap(sale.rawRecord).each {
                    Ticket(Name:it.name, Description:it.description, Price:it.price, Quantity:it.quantity)
                }
            }
            Totals(TotalAfterFeesAndTaxes:sale.totalAfterFeesAndTaxes, FeesAndTaxes:sale.taxes + sale.totalSurcharge)
            Customer(Name:sale.customerName, PhoneNumer:sale.phoneNumber, EmailAddress: sale.emailAddress)
        }
        writer.close()
        return writer.toString()
    }
}

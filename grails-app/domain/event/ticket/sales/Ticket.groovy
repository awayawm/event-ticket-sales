package event.ticket.sales

class Ticket {
    String name
    String description
    Double price
    byte[] ticketImage
    byte[] ticketLogo

    static constraints = {
        ticketImage maxSize: 1024 * 1024
        ticketLogo maxSize: 1024 * 1024
    }
}

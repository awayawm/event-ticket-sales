package event.ticket.sales

class Ticket {
    Double price
    String name
    String description
    byte[] ticketImage
    static belongsTo = [event: Event]

    static constraints = {
        poster maxSize: 1024 * 1024
    }
}

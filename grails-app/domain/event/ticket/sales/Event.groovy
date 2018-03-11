package event.ticket.sales

class Event {

    String name
    String shortURL
    String description
    String address
    byte[] poster
    Date doorsOpen
    Date eventStarts
    Date stopTicketSalesAt
    boolean enabled
    static hasMany = [tickets: Ticket]

    static constraints = {
        poster maxSize: 1024 * 1024
    }

}

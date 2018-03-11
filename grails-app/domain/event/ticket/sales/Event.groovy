package event.ticket.sales

class Event {

    static hasMany = [tickets: Ticket]
    String name
    String shortURL
    String description
    String address
    byte[] poster
    Date doorsOpen
    Date eventStarts
    Date stopTicketSalesAt
    boolean enabled


    static constraints = {
        poster maxSize: 1024 * 1024
    }

}

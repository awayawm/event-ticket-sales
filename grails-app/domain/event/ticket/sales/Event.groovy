package event.ticket.sales

class Event {

    static hasMany = [tickets: Ticket]
    String name
    String shortURL
    String description
    String address
    String posterName
    byte[] posterBytes
    String posterContentType
    Date doorsOpen
    Date eventStarts
    Date stopTicketSalesAt
    boolean enabled

    static constraints = {
        name blank: false
        shortURL blank: false
        address blank: false
        description blank: false
        tickets blank:false
        posterName blank: false
        posterContentType blank: false
        posterBytes blank: false, maxSize: 1024 * 1024 * 20
        doorsOpen blank: false
        eventStarts blank: false
        stopTicketSalesAt blank: false
        enabled blank: false

    }

}

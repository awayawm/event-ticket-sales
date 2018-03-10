package event.ticket.sales

class Ticket {
    Double price
    String name
    byte[] ticketImage
    static constraints = { }
    static mapping = {
        poster sqlType:'longblog'
    }
}

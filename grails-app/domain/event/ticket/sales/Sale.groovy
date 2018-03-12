package event.ticket.sales

class Sale {
    static hasMany = [tickets: Ticket]
    Event event
    Double total
    Double primaryPartyTotal
    Double secondaryPartyTotal
    String customerName
    String phoneNumber
    String emailAddress
    Date transactionDate
    static constraints = { }
}

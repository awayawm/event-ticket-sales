package event.ticket.sales

class Sale {
    Event event
    String rawRecord
    Double total
    Double primaryPartyTotal
    Double secondaryPartyTotal
    Double splitPercentage
    String customerName
    String phoneNumber
    String emailAddress
    Date transactionDate
    static constraints = { }
}

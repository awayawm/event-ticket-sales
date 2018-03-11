package event.ticket.sales

class Sale {
    String rawRecord // ticket_name, price, qty, ticket_name, price, qty, etc.
    Double total
    Double primaryPartyTotal
    Double secondaryPartyTotal
    String customerName
    String phoneNumber
    String emailAddress
    Date transactionDate
    static constraints = { }
}

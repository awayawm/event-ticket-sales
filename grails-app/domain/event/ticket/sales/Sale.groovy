package event.ticket.sales

class Sale {
    String itemsPurchased
    Double total
    Double primaryPartyTotal
    Double secondaryPartyTotal
    String customerName
    String phoneNumber
    String emailAddress
    Date transactionDate
    static constraints = { }
}

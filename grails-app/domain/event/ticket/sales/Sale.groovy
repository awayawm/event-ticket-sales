package event.ticket.sales

class Sale {
    Event event
    String rawRecord
    Double totalBeforeFeesAndTaxes
    Double totalAfterFeesAndTaxes
    Double taxes
    Double totalSurcharge
    Double primaryAllocation
    Double secondaryAllocation
    Double primaryPercentage
    boolean allocationEnabled
    String customerName
    String phoneNumber
    String emailAddress
    Date transactionDate
    static constraints = { }
}

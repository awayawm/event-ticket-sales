package event.ticket.sales

class PurchaseController {
    EventService purchaseService = new EventService()
    def index(){
        render view:"selectEvent", model:[events:purchaseService.getEvents().events]
    }
    def shortURL(){
        if (params.id) {
            def event = Event.findByShortURL(params.id)
            render model:[event:event], view:"selectTickets"
        }
    }
}

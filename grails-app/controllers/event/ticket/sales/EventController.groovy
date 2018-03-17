package event.ticket.sales

class EventController {

    DateService dateService = new DateService()

    def edit(){
        switch(request.method) {
            case ("GET"):
                if(params.id){
                    render view:"edit", model:[event:Event.findById(params.id), tickets:Ticket.findAll()]
                }else {
                    flash.message = "Nothing to see here :("
                    flash.class = "alert-danger"
                    redirect action: "index"
                }
                break
            case ("POST"):
                def event = Event.findById(params.id)
                event.name = params.name
                event.shortURL = params.shortURL
                event.description = params.description
                event.address = params.address

                if(params.eventStarts != "") {
                    event.eventStarts = new Date(params.eventStarts)
                }

                if(params.stopTicketSalesAt != "") {
                    event.stopTicketSalesAt = new Date(params.stopTicketSalesAt)
                }

                if(params.doorsOpen != "") {
                    event.doorsOpen = new Date(params.doorsOpen)
                }

                event.enabled = params.enabled == "on"

                if(!params.poster.isEmpty()) {
                    event.posterName = params.poster.getOriginalFilename()
                    event.posterBytes = params.poster.getBytes()
                    event.posterContentType = params.poster.getContentType()
                }

                event.tickets = []
                params.tickets.each {
                    event.addToTickets(Ticket.findById(Integer.parseInt(it)))
                }
                event.save(flush:true)

                if(event) {
                    flash.message = "Event successfully edited :)"
                    flash.class = "alert-success"
                    redirect action:"index"
                } else {
                    flash.message = "Event edit failed :("
                    flash.class = "alert-danger"
                    redirect action:"index"
                }
                break
        }
    }

    def delete(){
        if(params.id){
            def event = Event.findById(Integer.parseInt(params.id))
            event.delete(flush:true)
            flash.message = "Event has been deleted :)"
            flash.class = "alert-success"
            redirect action:"index"
        } else {
            flash.message = "Cannot delete without an id :("
            flash.class = "alert-danger"
            redirect action:"index"
        }
    }

    def index() {
        [events:Event.findAll()]
    }

    def create() {
        switch(request.method){
            case("GET"):
                render view: "create", model: [tickets: Ticket.findAll()]
                break
            case("POST"):
                def event = new Event(name:params.name, shortURL: params.shortURL, description: params.description,
                            address: params.address, eventStarts: new Date(params.eventStarts), stopTicketSalesAt: new Date(params.stopTicketSalesAt),
                            doorsOpen: new Date(params.doorsOpen), enabled: params.enabled == "on", posterName: params.poster.getOriginalFilename(),
                            posterBytes: params.poster.getBytes(), posterContentType: params.poster.getContentType())
                params.tickets.each {
                    event.addToTickets(Ticket.findById(Integer.parseInt(it)))
                }
                event = event.save(flush:true)
                if(!event){
                    flash.message = "Event not saved :( not sure why."
                    flash.class = "alert-danger"
                    redirect action:"create"
                }else{
                    flash.message = "Event saved successfully :)"
                    flash.class = "alert-success"
                    redirect action:"index"
                }
        }
    }
}

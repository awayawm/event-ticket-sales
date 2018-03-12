package event.ticket.sales

class EventController {

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
                println params
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

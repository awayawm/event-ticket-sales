package event.ticket.sales

class EventController {
    def index() {
        [events:Event.findAll()]
    }

    def create() {
        switch(request.method){
            case("GET"):
                render view:"create"
                break
            case("POST"):
                redirect action:create
                break
        }
    }
}

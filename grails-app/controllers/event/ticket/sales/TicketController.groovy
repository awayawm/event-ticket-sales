// TODO index view shows associations with events

package event.ticket.sales

class TicketController {

    def index() { }

    def create(){
        switch(request.method){
            case "POST":
                println "post"
                redirect([action: "index"])
                break
            case "GET":
                println "get"
                render([view: "create"])
                break
        }
    }
}

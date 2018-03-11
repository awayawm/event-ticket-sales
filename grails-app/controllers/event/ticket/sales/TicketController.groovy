// TODO index view shows associations with events

package event.ticket.sales

class TicketController {

    def index() { }

    def create(){
        switch(request.method){
            case "POST":
                if (params.ticket_name && params.ticket_price && params.ticket_description &&
                        params.ticket_ticketImage && params.ticket_ticketLogo) {
                    def ticket = new Ticket(name: params.ticket_name, description: params.ticket_description,
                                            price: params.ticket_price, ticketImage: params.ticket_ticketImage,
                                            ticketLogo: params.ticket_ticketLogo).save()
                    if (!ticket){
                        flash.message = "Error when saving ticket to database :("
                        flash.class = "error"
                        redirect(action: "create")
                    }else{
                        flash.message = "Ticket successfully created :)"
                        flash.class = "success"
                        redirect(action: "create")
                    }
                } else {
                    flash.message = "Not all parameters provided :("
                    flash.class = "error"
                    redirect(action: "create")
                }
                break
            case "GET":
                render([view: "create"])
                break
        }
    }
}

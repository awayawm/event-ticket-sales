// TODO index view shows associations with events

package event.ticket.sales

class TicketController {

    def index() {
        [tickets: Ticket.findAll()]
    }

    def create(){
        println params
        switch(request.method){
            case "POST":
                if (params.ticket_name && params.ticket_price && params.ticket_description &&
                        params.ticket_ticketImage && params.ticket_ticketLogo) {
                    def ticket = new Ticket(name: params.ticket_name, description: params.ticket_description,
                                            price: params.ticket_price, ticketImageBytes: params.ticket_ticketImage.getBytes(),
                                            ticketImageContentType: params.ticket_ticketImage.getContentType(),
                                            ticketImageName: params.ticket_ticketImage.getOriginalFilename(),
                                            ticketLogoName: params.ticket_ticketLogo.getOriginalFilename(),
                                            ticketLogoContentType: params.ticket_ticketLogo.getContentType(),
                                            ticketLogoBytes: params.ticket_ticketLogo.getBytes()).save()
                    if (!ticket){
                        flash.message = "Error when saving ticket to database :("
                        flash.class = "alert-danger"
                        redirect(action: "create")
                    }else{
                        flash.message = "Ticket successfully created :)"
                        flash.class = "alert-success"
                        redirect(action: "index")
                    }
                } else {
                    flash.message = "Not all parameters provided :("
                    flash.class = "alert-danger"
                    redirect(action: "create")
                }
                break
            case "GET":
                render([view: "create"])
                break
        }
    }
}

// TODO index view shows associations with events

package event.ticket.sales

class TicketController {
    def edit(){
        switch(request.method) {
            case("GET"):
                if (params.id) {
                    def ticket = Ticket.findById (params.id)
                    render (view: "edit", model: [ id: params.id,
                    ticket_name: ticket.name,
                    ticket_description: ticket.description,
                    ticket_price: ticket.price,
                    ticketImageName: ticket.ticketImageName,
                    ticketImageContentType: ticket.ticketImageContentType,
                    ticketImageBytes: ticket.ticketImageBytes,
                    ticketLogoName: ticket.ticketLogoName,
                    ticketLogoContentType: ticket.ticketLogoContentType,
                    ticketLogoBytes: ticket.ticketLogoBytes ])
                } else {
                    flash.message = "must pass a valid id :("
                    flash.class = "alert-danger"
                    redirect action: "index"
                }
                break

            case("POST"):
                def ticket = Ticket.findById(params.id)

                ticket.name = params.ticket_name
                ticket.description = params.ticket_description
                ticket.price = Double.parseDouble(params.ticket_price)

                if(!params.ticket_ticketImage.isEmpty()){
                    ticket.ticketImageBytes = params.ticket_ticketImage.getBytes()
                    ticket.ticketImageContentType = params.ticket_ticketImage.getContentType()
                    ticket.ticketImageName = params.ticket_ticketImage.getOriginalFilename()
                }
                if(!params.ticket_ticketLogo.isEmpty()){
                    ticket.ticketLogoName = params.ticket_ticketLogo.getOriginalFilename()
                    ticket.ticketLogoContentType = params.ticket_ticketLogo.getContentType()
                    ticket.ticketLogoBytes = params.ticket_ticketLogo.getBytes()
                }

                ticket = ticket.save()

                flash.message = "ticket successfully updated :)"
                flash.class = "alert-success"
                redirect action: "index"

                break
        }
    }


    def index() {
        [tickets: Ticket.findAll()]
    }

    def delete(){
        if(params.id){
            def ticket = Ticket.findById(Integer.parseInt(params.id))
            ticket.delete(flush:true)
            flash.message = "Ticket has been deleted :)"
            flash.class = "alert-success"
            redirect action:"index"
        } else {
            flash.message = "Cannot delete without an id :("
            flash.class = "alert-danger"
            redirect action:"index"
        }
    }

    def create(){
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

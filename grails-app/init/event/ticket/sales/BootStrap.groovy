package event.ticket.sales

import grails.util.Environment
import org.apache.commons.io.IOUtils


class BootStrap {

    def init = { servletContext ->
        def resourcePath = "${System.properties['user.dir']}/src/test/resources"
        byte[] titleBackground = IOUtils.toByteArray(new FileInputStream("${resourcePath}/ticketBackground.png"))
        byte[] titleBackground2 = IOUtils.toByteArray(new FileInputStream("${resourcePath}/ticketBackground2.jpg"))
        byte[] ticketLogo = IOUtils.toByteArray(new FileInputStream("${resourcePath}/ticketLogo.gif"))
        byte[] ticketLogo2 = IOUtils.toByteArray(new FileInputStream("${resourcePath}/ticketLogo2.jpg"))
        byte[] poster = IOUtils.toByteArray(new FileInputStream("${resourcePath}/poster.jpg"))
        byte[] poster2 = IOUtils.toByteArray(new FileInputStream("${resourcePath}/poster2.jpg"))
        byte[] poster3 = IOUtils.toByteArray(new FileInputStream("${resourcePath}/poster3.jpg"))

        switch(Environment.current){
            case(Environment.DEVELOPMENT):
                new Ticket(name: "General Admission", description: "Stadium Seating", price: "30.00",
                        ticketImageName: "titleBackground.png", ticketImageBytes: titleBackground, ticketImageContentType: "image/png",
                        ticketLogoName: "ticketLogo.gif", ticketLogoContentType: "image/gif", ticketLogoBytes: ticketLogo).save()
                  new Ticket(name: "Cage Seating", description: "Tables around the cage", price: "50.00",
                                    ticketImageName: "titleBackground2.jpg", ticketImageBytes: titleBackground2, ticketImageContentType: "image/jpg",
                                    ticketLogoName: "ticketLogo2.jpg", ticketLogoContentType: "image/jpg", ticketLogoBytes: ticketLogo2).save()
                break
            case(Environment.PRODUCTION):
                break
        }
    }
    def destroy = {
    }
}

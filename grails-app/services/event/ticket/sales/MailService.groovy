package event.ticket.sales

import grails.gorm.transactions.Transactional

import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.mail.Authenticator
import javax.mail.BodyPart
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import javax.mail.util.ByteArrayDataSource

@Transactional
class MailService {

    def sendTicketPdf(Sale sale) {

        ConfigService configService = new ConfigService()
        TicketService ticketService = new TicketService()

        def host = configService.getConfig().smtp.host.toString()
        def username = configService.getConfig().smtp.username.toString()
        def password = configService.getConfig().smtp.password.toString()
        def port = configService.getConfig().smtp.port.toString()
        def from = configService.getConfig().smtp.from.toString()
        def rootUrl = configService.getConfig().admin.rootUrl.toString()
        def adminTitle = configService.getConfig().admin.title.toString()
        def coordinator_name = configService.getConfig().admin.coordinator_name.toString()
        def coordinator_email = configService.getConfig().admin.coordinator_email.toString()
        def coordinator_phone_number = configService.getConfig().admin.coordinator_phone_number.toString()

        log.info host
        log.info username
        log.info password
        log.info port
        log.info from

        StringBuilder sb = new StringBuilder()
        ticketService.rawRecordToRawRecordItemMap(sale.rawRecord).each{
            sb.append("<tr><td>${it.name}</td><td>${it.quantity} @ ${it.price}</td></tr>")
        }
        sb.append("<tr><td>Taxes and Fees</td><td>${sale.taxes + sale.totalSurcharge}</td></tr>")
        sb.append("<tr><td><b>Total</b></td><td>${sale.totalAfterFeesAndTaxes}</td></tr>")

        String emailContents = """<html>
<body>
<h3>${sale.event.name}</h3>
<p>
Thank you for your online ticket purchase from ${adminTitle}.  Print out the ticket attached to the email and bring it to the event for entry.
</p>
<table>
${sb.toString()}
</table>
<h6>Contact ${coordinator_name} @ ${coordinator_phone_number} or ${coordinator_email} with questions about tickets.</h6>
<h4>
<a href="${rootUrl}/sale/status/${sale.uuid}">View your receipt</a>
</h4>
</body>
</html>"""

        Properties props = new Properties()
        props.put("mail.transport.protocol", "smtp")
        props.put("mail.smtp.host", host)
        props.put("mail.smtp.port", port)
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.starttls.enable", "true")
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password)
            }
        })

        Transport transport = session.getTransport()

        Message message = new MimeMessage(session)
        message.setSubject("${sale.event.name} Online Ticket Purchase")
        message.setFrom(new InternetAddress(from, "MFL Tickets"))
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(sale.emailAddress))

        BodyPart messageBodyPartHtml = new MimeBodyPart()
        messageBodyPartHtml.setText(emailContents, "utf-8", "html")

        MimeBodyPart messageBodyPart = new MimeBodyPart()
        Multipart multiPart = new MimeMultipart()

        ByteArrayDataSource bds = new ByteArrayDataSource(sale.ticketPDF, "application/pdf")
        messageBodyPart.setDataHandler(new DataHandler(bds))
        messageBodyPart.setFileName(bds.name)
        multiPart.addBodyPart(messageBodyPart)
        multiPart.addBodyPart(messageBodyPartHtml)

        message.setContent(multiPart)

        transport.connect()
        transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO))
        transport.close()
    }
}

package event.ticket.sales

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import grails.gorm.transactions.Transactional
import groovy.xml.MarkupBuilder
import org.apache.commons.io.FileUtils

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Transactional
class SaleService {

    def writer = new StringWriter()
    def xml = new MarkupBuilder(writer)
    TicketService ticketService = new TicketService()
    ConfigService configService = new ConfigService()

    private void getQrCode(String message, int width, int height, OutputStream outputStream){
        QRCodeWriter qrCodeWriter = new QRCodeWriter()
        BitMatrix bitMatrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, width, height)
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream)
    }

    def generateTicketXml(def sale) {
        def logoList = []
        def advertList = []
        def qrCodeList = [:]
        def logo
        def advert
        Ticket ticket
        Random random = new Random()
        OutputStream outputStream = new ByteArrayOutputStream()
        getQrCode((String)sale.uuid, 250, 250, outputStream)
        byte[] qrCode = outputStream.toByteArray()

        log.info "making xml for ${sale.uuid}"
        ticketService.rawRecordToRawRecordItemMap(sale.rawRecord).each{
            log.info "raw record is ${it}"
            ticket = Ticket.findByName(it.name)
            if(ticket != null) {
                log.info "found ticket for raw record, ${ticket}"
                advertList << [Bytes: ticket.ticketImageBytes, ContentType: ticket.ticketImageContentType]
                logoList << [Bytes: ticket.ticketLogoBytes, ContentType: ticket.ticketLogoContentType]
                qrCodeList << [Bytes: qrCode, ContentType: "image/png"]
            }
        }

        logo = logoList.get(random.nextInt(logoList.size()))
        advert = advertList.get(random.nextInt(advertList.size()))

        log.info "logo is ${logo}"
        log.info "advert is ${advert}"

        def now =  DateTimeFormatter.ofPattern("L/d/Y K:mm:ss a z").format(ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()))
        xml.Sale(){
            RenderDateTime(now)
            Coordinator(Phone:configService.getConfig().admin.coordinator_phone_number, Email:configService.getConfig().admin.coordinator_email)
            Images{
                Poster(Bytes:new String(Base64.getEncoder().encode(sale.event.posterBytes)), ContentType:sale.event.posterContentType)
                Logo(Bytes:new String(Base64.getEncoder().encode(logo.Bytes)), ContentType:logo.ContentType)
                Advert(Bytes:new String(Base64.getEncoder().encode(advert.Bytes)), ContentType:advert.ContentType)
                QrCode(Bytes:new String(Base64.getEncoder().encode(qrCodeList.Bytes)), ContentType:qrCodeList.ContentType)
            }
            UUID(sale.uuid)
            Event(Name:sale.event.name, Description: sale.event.description,
                    DoorsOpen:sale.event.doorsOpen, EventStarts: sale.event.eventStarts,
                    Address:sale.event.address)
            Tickets {
                ticketService.rawRecordToRawRecordItemMap(sale.rawRecord).each {
                    Ticket(Name:it.name, Description:it.description, Price:it.price, Quantity:it.quantity)
                }
            }
            Totals(TotalAfterFeesAndTaxes:sale.totalAfterFeesAndTaxes, FeesAndTaxes:sale.taxes + sale.totalSurcharge)
            Customer(Name:sale.customerName, PhoneNumer:sale.phoneNumber, EmailAddress: sale.emailAddress)
        }
        writer.close()
        return writer.toString()
    }
}

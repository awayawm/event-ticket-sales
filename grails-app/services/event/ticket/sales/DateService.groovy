package event.ticket.sales

import java.time.LocalDateTime
import java.time.ZoneId

class DateService {

    def getDate(int year, int month, int dayOfMonth, int hour, int minute){
        Date.from(LocalDateTime.of(year, month, dayOfMonth, hour, minute).atZone(ZoneId.systemDefault()).toInstant())
    }

}

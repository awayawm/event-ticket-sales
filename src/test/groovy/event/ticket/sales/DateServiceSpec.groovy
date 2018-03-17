package event.ticket.sales

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class DateServiceSpec extends Specification implements ServiceUnitTest<DateService>{
    def setup(){}
    def cleanup(){}

    def "does getDate() return a date"(){
        expect:
        service.getDate(2018, 6, 13, 17, 30) instanceof Date
    }
}

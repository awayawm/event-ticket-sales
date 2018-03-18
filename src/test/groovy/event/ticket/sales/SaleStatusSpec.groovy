package event.ticket.sales

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class SaleStatusSpec extends Specification implements DomainUnitTest<SaleStatus> {

    def setup() {
    }

    def cleanup() {
    }

    def "add SaleStatus object"(){
        when:
        new SaleStatus(transactionId: "68kep29z", status: "Approved").save()
        new SaleStatus(transactionId: "68kep29z", status: "Approved").save()
        then:
        SaleStatus.count() == 2
    }
}

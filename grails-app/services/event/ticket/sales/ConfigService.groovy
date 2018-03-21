package event.ticket.sales

import grails.gorm.transactions.Transactional

@Transactional
class ConfigService {

    boolean isEnvironmentalVariableSet() {
        !(System.getenv("EVENT_TICKET_SALES") == false || System.getenv("EVENT_TICKET_SALES") == null)
    }

    def getConfig(){
        switch(isEnvironmentalVariableSet()){
            case true:
                log.info "Configuration read from ${System.getenv("EVENT_TICKET_SALES")}"
                new ConfigSlurper().parse(new File(System.getenv("EVENT_TICKET_SALES")).text)
                break
            case false:
                log.info "Configuration read from ${this.class.classLoader.getResource("event-ticket-sales.config").toString()}"
                new ConfigSlurper().parse(this.class.classLoader.getResource("event-ticket-sales.config"))
                break
        }
    }
}

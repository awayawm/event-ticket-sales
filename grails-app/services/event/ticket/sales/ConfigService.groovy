package event.ticket.sales

import grails.gorm.transactions.Transactional

@Transactional
class ConfigService {

    boolean isEnvironmentalVariableSet() {
        log.info "System.getProperty: ${System.getProperty("EVENT_TICKET_SALES")}"
        !(System.getProperty("EVENT_TICKET_SALES") == false || System.getProperty("EVENT_TICKET_SALES") == null)
    }

    def getConfig(){
        switch(isEnvironmentalVariableSet()){
            case true:
                log.info "Configuration read from ${System.getProperty("EVENT_TICKET_SALES")}"
                new ConfigSlurper().parse(new File(System.getProperty("EVENT_TICKET_SALES")).text)
                break
            case false:
                log.info "Configuration read from ${this.class.classLoader.getResource("event-ticket-sales.config").toString()}"
                new ConfigSlurper().parse(this.class.classLoader.getResource("event-ticket-sales.config"))
                break
        }
    }
}

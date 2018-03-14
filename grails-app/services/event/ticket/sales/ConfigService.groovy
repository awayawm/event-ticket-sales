package event.ticket.sales

import grails.gorm.transactions.Transactional
import org.apache.commons.io.IOUtils

import java.nio.file.Path
import java.nio.file.Paths

@Transactional
class ConfigService {

    boolean isEnvironmentalVariableSet() {
        !(System.getenv("EVENT_TICKET_SALES") == false)
    }

    def getConfig(){
        switch(isEnvironmentalVariableSet()){
            case true:
                new ConfigSlurper().parse(new File(System.getenv("EVENT_TICKET_SALES")).text)
                break
            case false:
                new ConfigSlurper().parse(this.class.classLoader.getResource("event-ticket-sales.config"))
                break
        }
    }
}

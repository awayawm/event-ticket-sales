package event.ticket.sales

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class ConfigServiceSpec extends Specification implements ServiceUnitTest<ConfigService>{

    def setup() {
    }

    def cleanup() {
        System.metaClass = null
        service.metaClass = null
    }

    def "does isEnvironmentalVariableSet() return false when System.getEnv() returns null"(){
        setup:
        System.metaClass.static.getenv = { String name ->
            null
        }
        when:
        def result = service.isEnvironmentalVariableSet()
        then:
        !result
    }

    def "does isEnvironmentalVariableSet() return false if EVENT_TICKET_SALES not set"(){
        when:
        System.metaClass.static.getenv = { String name ->
            false
        }
        then:
        def result = service.isEnvironmentalVariableSet()

        expect:
        !result
    }

    def "does isEnvironmentalVariable() returns false if System.getenv() is false"(){
        System
    }

    def "does isEnvironmentalVariableSet() return true if EVENT_TICKET_SALES set"(){
        when:
        System.metaClass.static.getenv = { String name ->
            this.class.classLoader.getResource("event-ticket-sales.config")
        }
        then:
        def result = service.isEnvironmentalVariableSet()

        expect:
        result
    }

    def "does getConfig() return project config when EVENT_TICKET_SALES not set"(){
        when:
        service.metaClass.isEnvironmentalVariableSet = {
            false
        }
        then:
        service.getConfig().reports.report_emails == null
    }

    def  "does getConfig() return file system config when EVENT_TICKET_SALES set"(){
        when:
        System.metaClass.static.getenv = { String name ->
            def resourcePath = "${System.properties['user.dir']}/src/test/resources/valid.config"
            new File(resourcePath).absolutePath
        }

        then:
        service.getConfig().reports.report_emails[0] == "test@test"
    }

}

package event.ticket.sales

import com.braintreegateway.BraintreeGateway
import com.braintreegateway.exceptions.AuthenticationException
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class BraintreeServiceSpec extends Specification implements ServiceUnitTest<BraintreeService> {
    def setup(){
        ConfigService.metaClass.getConfig = { ->
            new ConfigSlurper().parse(
                    """
            keys {
                braintree {
                    merchantId = "merchantId"
                    publicKey = "publicKey"
                    privateKey = "privateKey"
                }
            }
            """)
        }
    }
    def cleanup(){
        ConfigService.metaClass = null
    }

    def "does getClientToken() return null with invalid credentials"(){
        setup:
        boolean authenticationExceptionThrown = false
        when :
        try {
            service.getClientToken()
        }catch(AuthenticationException ex){
            authenticationExceptionThrown = true
        }

        then:
        service.gateway instanceof BraintreeGateway
        authenticationExceptionThrown
    }

    def canTokenBeUpdated(){

    }
}

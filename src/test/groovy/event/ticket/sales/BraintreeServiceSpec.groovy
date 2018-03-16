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

    def "does getClientToken attempt to get a token"(){
        setup:
        boolean authenticationExceptionCaught = false

        expect:
        service.gateway instanceof BraintreeGateway
        try {
            service.getClientToken()
        } catch(AuthenticationException ex){
            authenticationExceptionCaught = true
        }
        authenticationExceptionCaught
    }
}

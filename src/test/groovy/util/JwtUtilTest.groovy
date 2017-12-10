package util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

class JwtUtilTest {

    @Test
    void canJwtUtilGenerateToken(){
        assertEquals JwtUtil.generateToken().getClass(), new String().getClass()
        assertNotNull JwtUtil.generateToken()
        assertNotNull JwtUtil.generateToken(5)
    }

    @Test
    void isDoesVerifyTokenAcceptValidTokensAndRejectExpiredTokens(){
        Algorithm algorithm = Algorithm.HMAC256("test")
        assertTrue JwtUtil.verifyToken(JwtUtil.generateToken(5))
        assertFalse JwtUtil.verifyToken(JWT.create().withExpiresAt(new Date(new Date().getTime() - 10)).sign(algorithm))
    }
}

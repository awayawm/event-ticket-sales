package util

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import org.apache.commons.lang3.time.DateUtils

class JwtUtil {

    static String generateToken() {
        try {
            Algorithm algorithm = Algorithm.HMAC256(ConfigUtil.getConfig().secretKey)
            return JWT.create().sign(algorithm)
        } catch (JWTCreationException ex){
            println ex.getStackTrace()
        }
    }

    static String generateToken(int minutes) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(ConfigUtil.getConfig().secretKey)
            JWT.create().withExpiresAt(DateUtils.addMinutes(new Date(), 5)).sign(algorithm)
        } catch (JWTCreationException ex){
            println ex.getStackTrace()
        }
    }

    static boolean verifyToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(ConfigUtil.getConfig().secretKey)
        JWTVerifier verifier = JWT.require(algorithm).build()
        try {
            verifier.verify(token).getToken()
            return true
        } catch (JWTVerificationException|UnsupportedEncodingException ex){
            println ex.getStackTrace()
            return false
        }
    }

}

package util

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import dao.AccountDAO
import dao.DAOFactory
import dao.RoleDAO
import entity.Account
import org.junit.After
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertFalse
import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue

class JwtUtilTest {

    DAOFactory daoFactory = new DAOFactory()
    AccountDAO accountDAO = daoFactory.getAccountDAO()
    RoleDAO roleDAO = daoFactory.getRoleDAO()

    @Before
    void setup(){
        accountDAO.removeAccountByUsername("test-account")
        daoFactory.deleteTestData()
        daoFactory.createTestData()
    }

    @After
    void teardown(){
        daoFactory.deleteTestData()
    }

    @Test
    void canJwtUtilGenerateToken(){
        assertEquals JwtUtil.generateToken().getClass(), new String().getClass()
        assertEquals JwtUtil.generateToken(5).getClass(), new String().getClass()
        assertEquals JwtUtil.generateToken(1,5).getClass(), new String().getClass()
    }

    @Test
    void canAccountIdBePullFromDecodedJWT(){
        Account account = accountDAO.addAccount("test-account",
                                                PasswordUtil.encryptString("test-password"),
                                                "test-email-account@test.com",
                                                roleDAO.getRoleByName("TestRoleNumber100").id)
        assertEquals account.id, JwtUtil.decodeJwt(JwtUtil.generateToken(account.id, 5)).getClaim("id").asInt()
        accountDAO.removeAccountById(account.id)
    }

    @Test
    void isDoesVerifyTokenAcceptValidTokensAndRejectExpiredTokens(){
        Algorithm algorithm = Algorithm.HMAC256("test")
        assertTrue JwtUtil.verifyToken(JwtUtil.generateToken(5))
        assertFalse JwtUtil.verifyToken(JWT.create().withExpiresAt(new Date(new Date().getTime() - 10)).sign(algorithm))
    }
}

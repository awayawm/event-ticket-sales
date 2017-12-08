package dao
import org.junit.After
import org.junit.Before
import org.junit.Test

import static org.junit.Assert.*

import entity.Role

class DAOFactoryTest {
    DAOFactory daoFactory = new DAOFactory()
    AccountDAO accountDAO
    RoleDAO roleDAO

    @Before
    void setUp() throws Exception {
        accountDAO = daoFactory.getAccountDAO()
        roleDAO = daoFactory.getRoleDAO()
    }

    @After
    void tearDown() throws Exception {
        daoFactory.closeConnection()
        accountDAO = null
        roleDAO = null
    }

    @Test
    void isTestDataCreated(){
        daoFactory.createTestData()
        assertEquals "TestRoleNumber100", roleDAO.getRoleByName("TestRoleNumber100").name
        assertEquals "TestRoleNumber101", roleDAO.getRoleByName("TestRoleNumber101").name
        assertEquals "TestUserNumber100", accountDAO.getAccountByUsername("TestUserNumber100").username
        assertEquals "TestUserNumber101", accountDAO.getAccountByUsername("TestUserNumber101").username
        daoFactory.deleteTestData()
    }

    @Test
    void isTestDataRemoved(){
        daoFactory.createTestData()
        daoFactory.deleteTestData()
        assertNull roleDAO.getRoleByName("TestRoleNumber100")
        assertNull roleDAO.getRoleByName("TestRoleNumber101")
        assertNull accountDAO.getAccountByUsername("TestUserNumber100")
        assertNull accountDAO.getAccountByUsername("TestUserNumber101")
    }

}
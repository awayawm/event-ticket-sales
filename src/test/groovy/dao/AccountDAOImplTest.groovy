package dao

import entity.Role
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.*

class AccountDAOImplTest {
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
        Role role
        daoFactory.createTestData()
        role = roleDAO.getRoleByName("TestAccountNumber100")
        assertEquals "TestAccountNumber100", role.name
        role = roleDAO.getRoleByName("TestAccountNumber101")
        assertEquals "TestAccountNumber101", role.name
        daoFactory.deleteTestData()
    }

    @Test
    void isTestDataRemoved(){
        daoFactory.createTestData()
        daoFactory.deleteTestData()
        assertNull roleDAO.getRoleByName("TestAccountNumber100")
        assertNull roleDAO.getRoleByName("TestAccountNumber101")
    }

    @Ignore
    @Test
    void testDoesFindByIdReturnAccount(){

    }



}
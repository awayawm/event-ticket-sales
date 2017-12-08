package dao

import entity.Account
import entity.Role
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import util.PasswordUtil

import static org.junit.Assert.*

class AccountDAOImplTest {
    DAOFactory daoFactory = new DAOFactory()
    AccountDAO accountDAO
    RoleDAO roleDAO
    Account account

    @Before
    void setUp() throws Exception {
        accountDAO = daoFactory.getAccountDAO()
        roleDAO = daoFactory.getRoleDAO()
        daoFactory.createTestData()

    }

    @After
    void tearDown() throws Exception {
        daoFactory.deleteTestData()
        daoFactory.closeConnection()
        accountDAO = null
        roleDAO = null
    }

    @Test
    void canAccountBeAdded(){
        account = accountDAO.addAccount("event",
                                        PasswordUtil.encryptString("sales"),
                                        "email@address.com",
                                        roleDAO.getRoleByName("TestRoleNumber100").id)
        assertEquals "event", account.username
    }

    @Test
    void canAccountsBeRemovedById(){
        account = accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "email@address.com",
                roleDAO.getRoleByName("TestRoleNumber100").id)
        assertTrue accountDAO.removeAccountById(account.id)
    }

    @Test
    void canAccountBeRemovedByUsername(){
        account = accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "email@address.com",
                roleDAO.getRoleByName("TestRoleNumber100").id)
        assertTrue accountDAO.removeAccountByUsername(account.username)
    }

}
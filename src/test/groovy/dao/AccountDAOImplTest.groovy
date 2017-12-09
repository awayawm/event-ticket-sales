package dao

import entity.Account
import entity.Role
import org.junit.After
import org.junit.Before
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
        assertNotNull account
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

    @Test
    void canGetAccountByName(){
        assertEquals "email@address.com", accountDAO.getAccountByUsername("TestUserNumber100").email
    }

    @Test
    void canGetAccountById(){
        assertEquals "email@address.com", accountDAO.getAccountById(accountDAO.getAccountByUsername("TestUserNumber100").id).email
    }

    @Test
    void getAccountByNameReturnsNullWhenNotFound(){
        assertNull accountDAO.getAccountByUsername("ThisAccountDoesntExist")
    }

    @Test
    void getAccountByIdReturnsNullWhenNotFound(){
        assertNull accountDAO.getAccountById(123456678)
    }

    @Test
    void doesAddAccountReturnAccountWithId(){
        account = accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "email@address.com",
                roleDAO.getRoleByName("TestRoleNumber100").id)
        assertNotEquals "The id of the added account should not be 0", 0, account.id
    }

    @Test
    void gracefullyHandlesUsernameUniqueConstraintViolationForMultipleAccounts(){
        accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "email@address.com",
                roleDAO.getRoleByName("TestRoleNumber100").id)

        assertNull accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "email@address.com",
                roleDAO.getRoleByName("TestRoleNumber100").id)

        assertTrue accountDAO.removeAccountByUsername("event")
    }

    @Test
    void canGetRoleByAccountId(){
        account = accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "email@address.com",
                roleDAO.getRoleByName("TestRoleNumber100").id)

        assertEquals new Role().getClass(), accountDAO.getRoleForAccountId(account.id).getClass()
        assertEquals "TestRoleNumber100", accountDAO.getRoleForAccountId(account.id).name
        assertNotNull accountDAO.getAccountByUsername("event")
    }

    @Test
    void canPasswordBeUpdated(){
        String result = ""
        account = accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "email@address.com",
                roleDAO.getRoleByName("TestRoleNumber100").id)

        assertTrue accountDAO.updatePasswordById(account.id, "new password")
        assertTrue PasswordUtil.verifyPassword(accountDAO.getAccountByUsername("event").password, "new password")
    }

}
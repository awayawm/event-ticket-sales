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
                                        "myemail@address.net",
                                        roleDAO.getRoleByName("TestRoleNumber100").id)
        assertEquals "event", account.username
        assertNotNull account
        accountDAO.removeAccountById(account.id)
    }

    @Test
    void canAccountsBeRemovedById(){
        account = accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "myemail@address.net",
                roleDAO.getRoleByName("TestRoleNumber100").id)
        assertTrue accountDAO.removeAccountById(account.id)
    }

    @Test
    void canAccountBeRemovedByUsername(){
        account = accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "myemail@address.net",
                roleDAO.getRoleByName("TestRoleNumber100").id)
        assertTrue accountDAO.removeAccountByUsername(account.username)
        accountDAO.removeAccountById(account.id)
    }

    @Test
    void canGetAccountByName(){
        assertEquals "event-email@address.com", accountDAO.getAccountByUsername("TestUserNumber100").email
    }

    @Test
    void canGetAccountById(){
        assertEquals "event-email@address.com", accountDAO.getAccountById(accountDAO.getAccountByUsername("TestUserNumber100").id).email
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
                "myemail@address.net",
                roleDAO.getRoleByName("TestRoleNumber100").id)
        assertNotEquals "The id of the added account should not be 0", 0, account.id
        accountDAO.removeAccountById(account.id)
    }

    @Test
    void gracefullyHandlesUsernameUniqueConstraintViolationForMultipleAccounts(){
        Account account = accountDAO.addAccount("event",
                            PasswordUtil.encryptString("sales"),
                            "myemail@address.net",
                            roleDAO.getRoleByName("TestRoleNumber100").id)

        assertNull accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "myemail@address.net",
                roleDAO.getRoleByName("TestRoleNumber100").id)

        assertTrue accountDAO.removeAccountByUsername("event")
        accountDAO.removeAccountById(account.id)
    }

    @Test
    void canGetRoleByAccountId(){
        account = accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "myemail@address.net",
                roleDAO.getRoleByName("TestRoleNumber100").id)

        assertEquals new Role().getClass(), accountDAO.getRoleForAccountId(account.id).getClass()
        assertEquals "TestRoleNumber100", accountDAO.getRoleForAccountId(account.id).name
        assertNotNull accountDAO.getAccountByUsername("event")
        accountDAO.removeAccountById(account.id)
    }

    @Test
    void canPasswordBeUpdated(){
        account = accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "myemail@address.net",
                roleDAO.getRoleByName("TestRoleNumber100").id)

        assertTrue accountDAO.updateAccountPasswordById(account.id, "new password")
        assertTrue PasswordUtil.verifyPassword(accountDAO.getAccountByUsername("event").password, "new password")
        accountDAO.removeAccountById(account.id)
    }

    @Test
    void canAccountPrimaryInformationBeUpdatedById(){
        account = accountDAO.addAccount("event",
                PasswordUtil.encryptString("sales"),
                "myemail@address.com",
                roleDAO.getRoleByName("TestRoleNumber100").id)

        assertEquals account.getClass(), accountDAO.updateAccountPrimaryInformationById(account.id, "Mr. Test Account", "test-account@email.com", "8675309").getClass()

        account = accountDAO.getAccountById(account.id)

        assertEquals "Mr. Test Account", account.fullname
        assertEquals "test-account@email.com", account.email
        assertEquals "8675309", account.phoneNumber
        assertEquals "Ms. Test Account", accountDAO.updateAccountPrimaryInformationById(account.id, "Ms. Test Account", "email", "phone").fullname

        accountDAO.removeAccountById(account.id)
    }

    @Test
    void canLastLoggedInBeUpdatedById(){
        account = accountDAO.getAccountByUsername("TestUserNumber100")
        assertNull account.lastLoggedIn
        assertEquals new Account().getClass(), accountDAO.updateAccountLastLoggedInById(account.id).getClass()
        assertNotNull accountDAO.getAccountById(account.id).lastLoggedIn
    }

    @Test
    void canLocationInformationBeUpdatedById(){
        account = accountDAO.getAccountByUsername("TestUserNumber100")
        accountDAO.updateAccountLocationInformationById(account.id, "625 Cherry St.", "St. Louis", "Missouri", "34411")
        assertEquals "625 Cherry St.", accountDAO.getAccountByUsername("TestUserNumber100").streetAddress
        assertEquals "St. Louis", accountDAO.getAccountByUsername("TestUserNumber100").city
        assertEquals "Missouri", accountDAO.getAccountByUsername("TestUserNumber100").state
        assertEquals "34411", accountDAO.getAccountByUsername("TestUserNumber100").zip

    }
}
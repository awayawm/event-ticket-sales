package dao

import org.junit.After;
import org.junit.Before
import org.junit.Test

class AccountDAOImplTest {
    @Before
    void setUp() throws Exception {
        AccountDAO accountDAO = new DAOFactory().getAccountDAO()
    }

    @After
    void tearDown() throws Exception {
    }

    @Test
    void testDoesFindByIdReturnAccount(){

    }

}
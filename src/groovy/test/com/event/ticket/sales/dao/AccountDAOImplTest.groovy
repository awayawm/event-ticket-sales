package com.event.ticket.sales.dao

import org.junit.After;
import org.junit.Before
import org.junit.Test
import com.event.ticket.sales.dao.AccountDAO
import com.event.ticket.sales.dao.DAOFactory;

import static org.junit.Assert.*

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
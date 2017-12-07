package com.event.ticket.sales.util

import org.junit.After
import org.junit.Before
import org.junit.Test
import com.event.ticket.sales.util.ConfigUtil

import static org.junit.Assert.*

class ConfigUtilTest {
    @Before
    void setUp() throws Exception {
    }

    @After
    void tearDown() throws Exception {
    }

    @Test
    void testSlurpsDBSettings(){
        assertTrue ConfigUtil.getConfig().db.containsKey('driverName')
        assertTrue ConfigUtil.getConfig().db.containsKey('username')
        assertTrue ConfigUtil.getConfig().db.containsKey('password')
        assertTrue ConfigUtil.getConfig().db.containsKey('url')
        assertTrue ConfigUtil.getConfig().containsKey('secretKey')
    }

}
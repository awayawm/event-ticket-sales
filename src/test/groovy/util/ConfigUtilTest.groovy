package util

import org.junit.Test

import static org.junit.Assert.assertTrue

class ConfigUtilTest {

    @Test
    void testSlurpsDBSettings(){
        assertTrue ConfigUtil.getConfig().db.containsKey('driver')
        assertTrue ConfigUtil.getConfig().db.containsKey('user')
        assertTrue ConfigUtil.getConfig().db.containsKey('password')
        assertTrue ConfigUtil.getConfig().db.containsKey('url')
        assertTrue ConfigUtil.getConfig().containsKey('secretKey')
    }

}
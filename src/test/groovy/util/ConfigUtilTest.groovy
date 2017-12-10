package util

import org.junit.Test

import static org.junit.Assert.assertTrue

class ConfigUtilTest {

    @Test
    void testSlurpsDBSettings(){
        assertTrue ConfigUtil.getFileConfig().db.containsKey('driver')
        assertTrue ConfigUtil.getFileConfig().db.containsKey('user')
        assertTrue ConfigUtil.getFileConfig().db.containsKey('password')
        assertTrue ConfigUtil.getFileConfig().db.containsKey('url')
        assertTrue ConfigUtil.getFileConfig().containsKey('secretKey')
    }

}
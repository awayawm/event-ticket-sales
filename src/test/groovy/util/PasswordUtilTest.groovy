package util

import org.junit.Test

import static org.junit.Assert.*

class PasswordUtilTest {

    String password = "event-ticket-sales"
    String encoded = "25fab3cac8b28044231ddba95c399efef6f141d9c07d1abca587a5e8a9257356"

    @Test
    void testEncryptStringEncodeStrings(){
        assertEquals encoded, PasswordUtil.encryptString(password)
    }

    @Test
    void testVerifyPasswordReturnsTrueWhenStringMatchesEncodedValue(){
        assertTrue PasswordUtil.verifyPassword(encoded, password)

    }
}

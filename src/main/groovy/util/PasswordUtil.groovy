package util

import org.apache.commons.codec.binary.Hex

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class PasswordUtil {

    static String encryptString(String password) {

        String key = ConfigUtil.getConfig().secretKey

        SecretKeySpec secretKeySpec = new SecretKeySpec(Arrays.copyOf(key.getBytes(), 16), "AES")
        Cipher cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, cipher.getParameters())

        return Hex.encodeHexString(cipher.doFinal(password.getBytes()))

    }

    static boolean verifyPassword(String encoded, String password){
        return encoded == encryptString(password)
    }


}

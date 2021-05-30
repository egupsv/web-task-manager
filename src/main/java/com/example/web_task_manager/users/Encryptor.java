package com.example.web_task_manager.users;

import com.example.web_task_manager.dba.TaskDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * The {@code Encryptor} class encrypts string (in this case class is used to encrypt password before this password will
 * be sent to server)
 */
public class Encryptor {
    /**
     * cryptographic cipher for encryption
     */
    private final Cipher cipher;
    SecretKey key;

    private static final Logger log = LoggerFactory.getLogger(Encryptor.class);

    /**
     * Initializes a newly created {@code Encryptor} object
     */
    public Encryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("AES");
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        int keySize = 256;
        keyGen.init(keySize);
        byte[] decodedKey = Base64.getDecoder().decode("TNIIGp0EctFoytVBrcTbnoC9A9XWWHVgF5A4XhUoQPc=");
        this.key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    /**
     * runs encryption of specific string
     *
     * @param password password inputted by user
     * @return The resulting encrypted password
     */
    public String encrypt(String password) {
        String result = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = password.getBytes(StandardCharsets.UTF_8);
            byte[] encryptedText = cipher.doFinal(plainText);
            result = Base64.getEncoder().encodeToString(encryptedText);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }
}

package authentication.com.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class AESEncryptionUtil {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public static String encrypt(String input, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedInput, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedInput);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(256); // Key size
        return keyGen.generateKey();
    }

    public static void main(String[] args) {
        try {
            SecretKey secretKey = generateKey();
            String rawPassword = "mySecretPassword";

            String encryptedPassword = encrypt(rawPassword, secretKey);
            System.out.println("Encrypted Password: " + encryptedPassword);

            String decryptedPassword = decrypt(encryptedPassword, secretKey);
            System.out.println("Decrypted Password: " + decryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


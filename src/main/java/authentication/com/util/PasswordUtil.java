package authentication.com.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /*public static boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }*/

    public static void main(String[] args) {
        String rawPassword = "mySecretPassword";
        String encryptedPassword = encryptPassword(rawPassword);

        System.out.println("Encrypted Password: " + encryptedPassword);
        System.out.println("Password matches: " + passwordEncoder.matches(rawPassword, encryptedPassword));

    }

}

package authentication.com.config;

public interface MyPasswordEncoder {

    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);

    default boolean upgradeEncoding(String encodedPassword) {
        return false;
    }

    //String getDecodePassword(String encodedPassword);
}

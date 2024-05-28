package authentication.com.config;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.util.EncodingUtils;

import java.security.MessageDigest;

public abstract class MyAbstractPasswordEncoder implements MyPasswordEncoder {

    private final BytesKeyGenerator saltGenerator = KeyGenerators.secureRandom();

    protected MyAbstractPasswordEncoder() {
    }

    public String encode(CharSequence rawPassword) {
        byte[] salt = this.saltGenerator.generateKey();
        byte[] encoded = this.encodeAndConcatenate(rawPassword, salt);
        return String.valueOf(Hex.encode(encoded));
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        byte[] digested = Hex.decode(encodedPassword);
        byte[] salt = EncodingUtils.subArray(digested, 0, this.saltGenerator.getKeyLength());
        return matches(digested, this.encodeAndConcatenate(rawPassword, salt));
    }

    protected abstract byte[] encode(CharSequence rawPassword, byte[] salt);

    protected byte[] encodeAndConcatenate(CharSequence rawPassword, byte[] salt) {
        return EncodingUtils.concatenate(new byte[][]{salt, this.encode(rawPassword, salt)});
    }

    protected static boolean matches(byte[] expected, byte[] actual) {
        return MessageDigest.isEqual(expected, actual);
    }
}

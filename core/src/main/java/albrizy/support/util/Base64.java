package albrizy.support.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Base64 {

    private static final byte[] SALTED = "Salted__".getBytes(US_ASCII);
    private static final String MODE = "AES/CBC/PKCS5Padding";

    private Base64() {}

    public static String encode(String input, String key) throws Exception {
        String rotKey = Base64.rotate(key);
        return android.util.Base64.encodeToString(
                Base64.encrypt(input.getBytes(UTF_8), rotKey.getBytes(UTF_8)),
                android.util.Base64.NO_WRAP
        );
    }

    public static String decode(String input, String key) throws Exception {
        String rotKey = Base64.rotate(key);
        return new String(Base64.decrypt(
                android.util.Base64.decode(input, android.util.Base64.NO_WRAP),
                rotKey.getBytes(UTF_8)), UTF_8
        );
    }

    private static byte[] encrypt(byte[] key, byte[] input) throws Exception {
        byte[] salt = (new SecureRandom()).generateSeed(8);
        Object[] keyIv = deriveKeyAndIv(key, salt);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec((byte[])keyIv[0], "AES"),
                new IvParameterSpec((byte[])keyIv[1])
        );
        byte[] enc = cipher.doFinal(input);
        return concat(concat(SALTED, salt), enc);
    }

    private static byte[] decrypt(byte[] key, byte[] input) throws Exception {
        byte[] salt = Arrays.copyOfRange(input, 8, 16);
        if (!Arrays.equals(Arrays.copyOfRange(input, 0, 8), SALTED)) {
            throw new IllegalArgumentException("Invalid crypted data");
        }
        Object[] keyIv = deriveKeyAndIv(key, salt);
        Cipher cipher = Cipher.getInstance(MODE);
        cipher.init(Cipher.DECRYPT_MODE,
                new SecretKeySpec((byte[])keyIv[0], "AES"),
                new IvParameterSpec((byte[])keyIv[1])
        );
        return cipher.doFinal(input, 16, input.length - 16);
    }

    private static String rotate(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            sb.append((char) (input.codePointAt(i) + 16));
        }
        return sb.toString();
    }

    private static Object[] deriveKeyAndIv(byte[] passphrase, byte[] salt) throws Exception {
        final MessageDigest md5 = MessageDigest.getInstance("MD5");
        final byte[] passSalt = concat(passphrase, salt);
        byte[] dx = new byte[0];
        byte[] di = new byte[0];
        for (int i = 0; i < 3; i++) {
            di = md5.digest(concat(di, passSalt));
            dx = concat(dx, di);
        }

        return new Object[]{
                Arrays.copyOfRange(dx, 0, 32),
                Arrays.copyOfRange(dx, 32, 48)
        };
    }

    private static byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}

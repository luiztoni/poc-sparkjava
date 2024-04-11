package br.luiz.toni.sparkjava.poc.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class Password {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final Random RANDOM = new SecureRandom();
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String salt(int length) {
        StringBuilder salt = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            salt.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return salt.toString();
    }

    private static byte[] hash(char[] password, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException exception) {
            throw new AssertionError("Hash error: " + exception.getMessage(), exception);
        } finally {
            spec.clearPassword();
        }
    }

    public static String encode(String password, String salt) {
        byte[] bytes = hash(password.toCharArray(), salt.getBytes());
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static boolean equals(String password, String hash, String salt) {
        password = encode(password, salt);
        return password.equalsIgnoreCase(hash);
    }
}
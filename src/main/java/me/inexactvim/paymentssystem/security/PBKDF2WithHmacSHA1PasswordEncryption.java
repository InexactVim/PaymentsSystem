package me.inexactvim.paymentssystem.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.inexactvim.paymentssystem.util.HexUtil;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PBKDF2WithHmacSHA1PasswordEncryption implements PasswordEncryption {

    @Getter
    private static final PBKDF2WithHmacSHA1PasswordEncryption instance = new PBKDF2WithHmacSHA1PasswordEncryption();

    private static final int ITERATIONS = 10;
    private static final int SALT_BYTES = 8;
    private static final int HASH_BYTES = 16;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    @Override
    public String encrypt(String password) {
        byte[] salt = generateSalt();
        byte[] hash = getHash(password.toCharArray(), salt, ITERATIONS, HASH_BYTES);

        if (hash.length > 0) {
            return ITERATIONS + ":" + HexUtil.toHex(salt) + ":" + HexUtil.toHex(hash);
        } else {
            return "0:" + password;
        }
    }

    private byte[] generateSalt() {
        byte[] salt = new byte[SALT_BYTES];
        SECURE_RANDOM.nextBytes(salt);
        return salt;
    }

    private byte[] getHash(char[] chars, byte[] salt, int iterations, int hashLength) {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(chars, salt, iterations, hashLength * 8);

        try {
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(pbeKeySpec).getEncoded();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public boolean isValid(String target, String encryptedPassword) {
        String[] arguments = encryptedPassword.split(":");
        int iterations = Integer.parseInt(arguments[0]);

        if (iterations == 0 || arguments.length < 3) {
            return target.equals(encryptedPassword);
        }

        byte[] salt = HexUtil.fromHex(arguments[1]);
        byte[] hash = HexUtil.fromHex(arguments[2]);
        byte[] testHash = getHash(target.toCharArray(), salt, iterations, hash.length);

        return slowEquals(hash, testHash);
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;

        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }

        return diff == 0;
    }
}

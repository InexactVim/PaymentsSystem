package me.inexactvim.paymentssystem.util;

import java.math.BigInteger;

public class HexUtil {

    public static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];

        for(int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }

        return binary;
    }

    public static String toHex(byte[] array) {
        BigInteger integer = new BigInteger(1, array);
        String hex = integer.toString(16);
        int paddingLength = (array.length * 2) - hex.length();

        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}

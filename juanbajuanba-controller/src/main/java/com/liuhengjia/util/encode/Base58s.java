package com.liuhengjia.util.encode;

import java.util.Arrays;

public class Base58s {
    protected static final char[] BASE58_ENCODE_TABLE = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    private static final char ENCODED_ZERO;
    private static final int[] INDEXES;

    static {
        ENCODED_ZERO = BASE58_ENCODE_TABLE[0];
        INDEXES = new int[128];
        Arrays.fill(INDEXES, -1);

        for (int i = 0; i < BASE58_ENCODE_TABLE.length; INDEXES[BASE58_ENCODE_TABLE[i]] = i++) {
        }

    }

    private Base58s() {
    }

    public static String encode(byte[] input) {
        if (input.length == 0) {
            return "";
        } else {
            int zeros;
            for (zeros = 0; zeros < input.length && input[zeros] == 0; ++zeros) {
            }

            input = Arrays.copyOf(input, input.length);
            char[] encoded = new char[input.length * 2];
            int outputStart = encoded.length;
            int inputStart = zeros;

            while (inputStart < input.length) {
                outputStart--;
                encoded[outputStart] = BASE58_ENCODE_TABLE[divmod(input, inputStart, 256, 58)];
                if (input[inputStart] == 0) {
                    inputStart++;
                }
            }

            while (outputStart < encoded.length && encoded[outputStart] == ENCODED_ZERO) {
                outputStart++;
            }

            while (true) {
                zeros--;
                if (zeros < 0) {
                    return new String(encoded, outputStart, encoded.length - outputStart);
                }

                outputStart--;
                encoded[outputStart] = ENCODED_ZERO;
            }
        }
    }

    public static byte[] decode(String input) {
        if (input.isEmpty()) {
            return new byte[0];
        } else {
            byte[] input58 = new byte[input.length()];

            int zeros;
            int outputStart;
            for (zeros = 0; zeros < input.length(); zeros++) {
                char c = input.charAt(zeros);
                outputStart = c < 128 ? INDEXES[c] : -1;
                if (outputStart < 0) {
                    String msg = "Invalid characters,c=" + c;
                    throw new RuntimeException(msg);
                }
                input58[zeros] = (byte) outputStart;
            }

            for (zeros = 0; zeros < input58.length && input58[zeros] == 0; ++zeros) {
            }

            byte[] decoded = new byte[input.length()];
            outputStart = decoded.length;
            int inputStart = zeros;

            while (inputStart < input58.length) {
                outputStart--;
                decoded[outputStart] = divmod(input58, inputStart, 58, 256);
                if (input58[inputStart] == 0) {
                    inputStart++;
                }
            }

            while (outputStart < decoded.length && decoded[outputStart] == 0) {
                outputStart++;
            }

            return Arrays.copyOfRange(decoded, outputStart - zeros, decoded.length);
        }
    }

    private static byte divmod(byte[] number, int firstDigit, int base, int divisor) {
        int remainder = 0;

        for (int i = firstDigit; i < number.length; ++i) {
            int digit = number[i] & 255;
            int temp = remainder * base + digit;
            number[i] = (byte) (temp / divisor);
            remainder = temp % divisor;
        }

        return (byte) remainder;
    }
}


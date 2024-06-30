package com.liuhengjia.util.encode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHAs {
    private SHAs() {
    }

    public static String getSHA256(String str) {
        String encodestr = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        }
        return encodestr;
    }

    public static String byte2Hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        String temp = null;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 255);
            if (temp.length() == 1) {
                sb.append("0");
            }
            sb.append(temp);
        }
        return sb.toString();
    }
}

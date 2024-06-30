package com.liuhengjia.util.encode;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5s {
    public MD5s() {
    }

    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException var4) {
            throw new RuntimeException("没有这个md5算法！");
        }
        StringBuilder md5code = new StringBuilder((new BigInteger(1, secretBytes)).toString(16));
        for (int i = 0; i < 32 - md5code.length(); ++i) {
            md5code.append("0");
        }
        return md5code.toString();
    }
}

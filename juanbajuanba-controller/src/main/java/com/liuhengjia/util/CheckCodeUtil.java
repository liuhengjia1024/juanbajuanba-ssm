package com.liuhengjia.util;

import java.util.Random;

public class CheckCodeUtil {
    private static final Random RANDOM = new Random();

    private CheckCodeUtil() {
    }

    public static String getCheckCode() {
        String aZ = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String base = "0123456789" + aZ + aZ.toLowerCase();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 4; ++i) {
            sb.append(base.charAt(RANDOM.nextInt(base.length())));
        }
        return sb.toString();
    }
}

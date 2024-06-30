package com.liuhengjia.util.encode;

public class Encodes {
    private final String password;

    public Encodes(String password) {
        this.password = password;
    }

    public String getEncodePassword() {
        // 加盐
        String passwordCopy = "MD" + this.password + "5";
        passwordCopy = MD5s.stringToMD5(passwordCopy);
        // 2 次加盐
        String checkCode = SHAs.getSHA256(SHAs.getSHA256(passwordCopy)).substring(0, 4);
        passwordCopy = passwordCopy + checkCode;
        // 加密
        passwordCopy = SHAs.byte2Hex(passwordCopy.getBytes());
        passwordCopy = Base58s.encode(passwordCopy.getBytes());
        passwordCopy = SHAs.byte2Hex(passwordCopy.getBytes());
        passwordCopy = Base58s.encode(passwordCopy.getBytes());
        String encodePassword = passwordCopy;
        StringBuilder lastSb = new StringBuilder();
        // 稀释
        for(int i = 0; i < encodePassword.length(); i++) {
            if (i % 5 == 0) {
                lastSb.append(encodePassword.charAt(i));
            }
        }
        return new String(lastSb);
    }
}
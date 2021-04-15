package com.atming.utils;

import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author: Annoming
 * @Date: 2021/1/3
 * @Time: 17:39
 * @Description
 */
public class PasswordSaltUtil {
    private static Logger pLogger = Logger.getLogger(PasswordSaltUtil.class); //日志记录
    private final static String[] HEX_DIGITS = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "!", "@", "#", "a", "b", "c", "d", "*", "e", "$",
            "f", "g", "%", "h", "&", "i", "j", "/"
    };
    private Object salt;
    private String algorithm;

    public PasswordSaltUtil(Object salt, String algorithm) {
        this.salt = salt;
        this.algorithm = algorithm;
    }

    /**
     * 加密
     *
     * @param rawPass
     * @return
     */
    public String encode(String rawPass) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes(StandardCharsets.UTF_8)));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断密码是否正确
     *
     * @param encPass 加密后的密码
     * @param rawPass 原始密码
     * @return boolean
     */
    public boolean isPasswordValid(String encPass, String rawPass) {
        String pass1 = "" + encPass;
        String pass2 = encode(rawPass);
        return pass1.equals(pass2);
    }

    private String mergePasswordAndSalt(String password) {
        if (password == null) {
            password = "";
        }
        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }

    private String byteArrayToHexString(byte[] b) {
        StringBuilder result = new StringBuilder();
        for (byte value : b) {
            result.append(byteToHexString(value));
        }
        return result.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / HEX_DIGITS.length;
        int d2 = n % HEX_DIGITS.length;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];
    }

    public String createRandomPass() {
        StringBuffer flag = null;
        for (int i = 0; i <= 100; i++) {
            String sources = "0123456789";
            Random rand = new Random();
            flag = new StringBuffer();
            for (int j = 0; j < 6; j++) {
                flag.append(sources.charAt(rand.nextInt(9)) + "");
            }
        }
        if (flag == null) {
            pLogger.error("生成随机密码失败");
        }
        return flag.toString();
    }
}

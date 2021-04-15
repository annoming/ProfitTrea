package com.atming.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author annoming
 * @date 2021/4/1 3:42 下午
 */

public class ShellUtil {
    public static String call(String[] str) throws IOException {
        if (str == null || str.length == 0) {
            return null;
        }
        Process process = Runtime.getRuntime().exec(str);
        BufferedReader strCon = new BufferedReader(new InputStreamReader(
                process.getInputStream()));
        String line;
        StringBuffer sb = new StringBuffer();
        while ((line = strCon.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        strCon.close();
        process.destroy();
        return sb.toString();
    }

}

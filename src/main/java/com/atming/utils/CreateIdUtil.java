package com.atming.utils;

import org.apache.log4j.Logger;

import java.util.Random;

/**
 * @author annoming
 * @date 2021/3/25 7:34 上午
 */

public class CreateIdUtil {

    private static Logger cLogger = Logger.getLogger(CreateIdUtil.class);

    public static String createId(String idName) {
        String dbName = "profittrea_" + idName;
        String sql = "select " + idName + "_id from " + dbName + " order by " + idName + "_id desc limit 0,1";
        //查询数据库中最后一条数据的id
        String id =  DbUtil.queryData(sql);
        if (id != null) {
            int number = Integer.parseInt(id.split("-")[1]);
            number++;
            String lastRecord = number + "";
            int length = lastRecord.length();
            for (int i = 0; i < 5 - length; i++) {
                lastRecord =  "0" + lastRecord;
            }
            return "PR" + idName.substring(0,1).toUpperCase() + "-" + lastRecord;
        }else{
            return "PR" + idName.charAt(0) + "-00001";
        }
    }

    public static String createRandomId(Integer number) {
        StringBuffer flag = null;
        for (int i = 0; i <= 100; i++) {
            String sources = "0123456789";
            Random rand = new Random();
            flag = new StringBuffer();
            for (int j = 0; j < number; j++) {
                flag.append(sources.charAt(rand.nextInt(9)) + "");
            }
        }
        return flag.toString();
    }
}

package com.atming.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author annoming
 * @date 2021/4/10 10:25 下午
 */

public class DateTransformUtil {


    public static Date transformToTime(String date) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        Date time = null;
        try {
            time = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String transformToString() {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sf.format(now);
    }

    public static String transformToDate() {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        return sf.format(now);
    }

    public static Date transformStringTime(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date =  sf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String transformToTime() {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
        return sf.format(now);
    }

    public static Date transformNowDate(String time) {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(now) + " " + time + ":00";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowDay = null;
        try {
            nowDay = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nowDay;
    }
}

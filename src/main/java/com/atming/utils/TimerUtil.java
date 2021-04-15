package com.atming.utils;

import com.atming.mapper.CronMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.concurrent.TimeUnit;

/**
 * @author annoming
 * @date 2021/3/27 11:56 下午
 */

public class TimerUtil {

    @Autowired
    private CronMapper cronMapper;

    private static Logger tLogger = Logger.getLogger(TimeUnit.class); //日志记录
    private static String shell_path = "/Users/mingrui/PycharmProjects/pythonProject/login.py";

    @Bean
    public String getCronValue() {
        String cron = cronMapper.findById("PRJ-00001");
        System.out.println(cron);
        return cron;
    }

    @Bean
    public String getNewBlockCron(){
        String cron = cronMapper.findById("PRJ-00002");
        System.out.println(cron);
        return cron;
    }

    /***
     * 定时发送邮件任务
     */
    @Scheduled(cron = "#{@getCronValue}")
    public void run() {
        tLogger.info("==========mailSend job start===========");
        try {
            /*if (!mailSend()) {
                tLogger.error("send mail error");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            tLogger.error("send error");
        }
        tLogger.info("send mail success");
    }


    /**
     * 爬取新三板数据
     */
    @Scheduled(cron = "#{@getNewBlockCron}")
    public void excute() {
        Process pr = null;
        tLogger.info("==========getData job start===========");
        System.out.println("============开始爬取新三板数据=============");
        try {
            pr = Runtime.getRuntime().exec("/Users/mingrui/PycharmProjects/pythonProject/venv/bin/python /Users/mingrui/PycharmProjects/pythonProject/login.py");
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            tLogger.error("爬取数据异常");
        }finally{
            if (pr != null) {
                pr.destroy();
            }
        }
        System.out.println("================爬取新三板数据成功===============");
        tLogger.info("爬取数据成功");
    }


}

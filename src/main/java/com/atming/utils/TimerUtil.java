package com.atming.utils;

import com.atming.mapper.CronMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.TimeUnit;

/**
 * @author annoming
 * @date 2021/3/27 11:56 下午
 */

public class TimerUtil {



    @Autowired
    private CronMapper cronMapper;
    private JavaMailSenderImpl mailSender;
    private SimpleMailMessage templateMail;
    private static Logger tLogger = Logger.getLogger(TimeUnit.class); //日志记录

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMail(SimpleMailMessage templateMail) {
        this.templateMail = templateMail;
    }

    @Bean
    public String getCronValue(){
        String cron = cronMapper.findById("PRJ-00001");
        System.out.println(cron);
        return cron;
    }

    /***
     * 定时发送邮件任务
     */
    @Scheduled(cron = "#{@getCronValue}")
    public void run(){
        tLogger.info("==========mailSend job start===========");
        try {
            if(!mailSend()) {
                tLogger.error("send mail error");
            }
        }catch (Exception e){
            e.printStackTrace();
            tLogger.error("send error");
        }
        tLogger.info("send mail success");
    }


    @Scheduled(cron = "")
    public void excute(){
        tLogger.info("==========getData job start===========");
        System.out.println("测试爬取数据");
        try {
            if (!getDatas()) {
                tLogger.info("get data fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            tLogger.error("get data job error");
        }
        tLogger.info("get data success");
    }

    /**
     * 发送邮件
     * @return
     */
    private boolean mailSend() {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(mailSender.getUsername());
        mail.setTo(templateMail.getTo());
        mail.setSubject(templateMail.getSubject());
        mail.setText("测试发送邮件");
        mailSender.send(mail);
        return true;
    }

    private boolean getDatas(){

        return  true;
    }
}

package com.atming.utils;

import com.atming.entity.User;
import org.apache.log4j.Logger;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author annoming
 * @date 2021/4/7 10:57 上午
 */

public class EmailUtil {

    private static Logger eLogger = Logger.getLogger(EmailUtil.class);

    private JavaMailSenderImpl mailSend;
    private SimpleMailMessage template;

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSend = mailSender;
    }

    public void setTemplateMail(SimpleMailMessage templateMail) {
        this.template = templateMail;
    }

    public void run(User user,String randomPass) {
        eLogger.info("==========mailSend start===========");
        try {
            if (!mailSend(user,randomPass)) {
                eLogger.error("send mail error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            eLogger.error("send error");
        }
        eLogger.info("send mail success");
        System.out.println("邮件发送成功");
    }


    /**
     * 发送邮件
     *
     * @return
     */
    private boolean mailSend(User user, String randomPass) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        String date = sf.format(new Date());
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(mailSend.getUsername());
        mail.setTo(user.getEmail());
        mail.setSubject(template.getSubject());
        mail.setText("亲爱的" + user.getUserName() + ":\n欢迎注册使用Profittrea证券模拟系统。\n您的初始密码为" + randomPass + "，请登录系统后修改密码\n此致\nannoming\n" + date);
        mailSend.send(mail);
        return true;
    }
}

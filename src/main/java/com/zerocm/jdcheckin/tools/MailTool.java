package com.zerocm.jdcheckin.tools;

import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @功能:
 * @项目名:jdcheckin
 * @作者:0cm
 * @日期:2020/4/73:08 下午
 */
@Service
public class MailTool {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String mailFrom;
    private String mailTo;

    public void MailTool(String mailTo){
        this.mailTo = mailTo;
    }

    public void sendSimpleMail(String text) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            //邮件接收人
            simpleMailMessage.setFrom(mailFrom);
            //邮件接收人
            simpleMailMessage.setTo(mailTo);
            //邮件主题
            simpleMailMessage.setSubject("京东签到");
            //邮件内容
            simpleMailMessage.setText(text);
            logger.info("--------邮件发送中---------");
            javaMailSender.send(simpleMailMessage);
            logger.info("--------邮件发送成功---------");
            logger.info("\n" +
                    "||'''''''''''''''''''''''''''''''''''''''''''''''''''||\n" +
                    "||     ||||||||| ||||||||  ||||||||| ||||||||| MMMMMM||\n" +
                    "||      ,,,, ||  ||    ||   ,,,, ||  ||  ||    MMMMMM||\n" +
                    "||      |,,| ||  ||    ||   |,,| ||  ''''||''''      ||\n" +
                    "||          '||  ||||||||       '||  /; '||  ;\\      ||\n" +
                    "||              ,,,, dddddddddddddP'                 ||\n" +
                    "||MMMMMMMMMMMMMMMMMMMMMMMBBbbb                       ||\n" +
                    "||YMMMMMMMMMMMMMMMMMMKPPP''           Coca Cola      ||\n" +
                    "||,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,||\n" +
                    "                                                       \n" +
                    "                       Coca-cola.(Chinese:0cm大佬)");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

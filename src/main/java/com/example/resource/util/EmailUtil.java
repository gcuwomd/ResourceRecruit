package com.example.resource.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
//发送邮箱验证码
@Component
public class EmailUtil {
    /**
     * 值再yml里
    * */
    @Value("${form}")
    private  String form;
    private final JavaMailSender javaMailSender;
/**
 * 构造注入
 * */
    @Autowired
    public EmailUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
/**
* @param to  发送地址
* @param subject  title
* @param text  发送的内容，一般是生成的验证码，可以优化
* */
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(form);
        javaMailSender.send(message);
    }
}

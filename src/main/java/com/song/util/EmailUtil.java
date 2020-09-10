package com.song.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author 宋涛
 * @version 1.0
 * @date 2020/7/28 14:55
 */
@Component
@Slf4j
public class EmailUtil {


    @Autowired
    private JavaMailSender javaMailSender;


    public  void SendEmail(String message,String e) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setSubject("系统出现错误！！！！");
        messageHelper.setFrom("508436045@qq.com");
        messageHelper.setTo("1767821837@qq.com");
        messageHelper.setText("<h1>" + "" + "错误原因" + message+"      "+e, true);
        javaMailSender.send(messageHelper.getMimeMessage());

    }


}

package com.fengyun.mail;

import com.fengyun.mail.service.impl.MailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.mail.MessagingException;


@SpringBootTest
public class MailServiceTest {

    @Resource
    MailServiceImpl mailService;

    //测试简单文本文件
    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("123456789@163.com", "Springboot-Mail测试", "基于SpringBoot的邮件系统的文本邮件",1L);
    }

    //发送HTML邮件
    @Test
    public void sendHtmlMail() throws MessagingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html>\n").append("<body>\n").append("<h1>HTML邮件系统测试</h1>\n").append("</body>\n").append("</html>\n");
        //String content = "<html>\n"+"<body>\n"+"<h1>HTML邮件系统测试</h1>\n"+"</body>\n"+"</html>\n";
        mailService.sendHtmlMail("123456789@163.com", "HTML邮件测试", String.valueOf(stringBuilder),1L);
    }


}

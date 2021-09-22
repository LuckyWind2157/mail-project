package com.fengyun.mail.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;


//@Service
public class MailServiceImpl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //指定发送者
   // @Value("${spring.mail.username}")
    private String from;

   // @Autowired
    private JavaMailSender mailSender;

    public void sayHello() {
        System.out.println("Hello World");
    }

    /*
     * 发送简单文本邮件
     * to：发送给谁
     * subject：发送的主题（邮件主题）
     * content：发送的内容
     * */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        simpleMailMessage.setFrom(from);

        //发送邮件
        try {
            mailSender.send(simpleMailMessage);
            logger.info("简单邮件发送成功");
        } catch (MailException e) {
            logger.error("简单邮件发送失败", e);
        }
    }

    /*
     * 发送HTML邮件
     * to：发送给谁
     * subject：发送的主题（邮件主题）
     * content：发送的内容
     * */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {

        MimeMessage mimeMailMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        mimeMessageHelper.setFrom(from);

        try {
            mailSender.send(mimeMailMessage);
            logger.info("HTML邮件发送成功！");
        } catch (MailException e) {
            logger.error("HTML邮件发送失败！", e);
        }
    }

    /*
     * 发送附件邮件
     * to：发送给谁
     * subject：发送的主题（邮件主题）
     * content：发送的内容
     * filePath：附件路径
     * */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content);

        //获取文件路径以及文件名称
        FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
        String fileName = fileSystemResource.getFilename();

        mimeMessageHelper.addAttachment(fileName, fileSystemResource);

        mimeMessageHelper.setFrom(from);

        try {
            mailSender.send(mimeMessage);
            logger.info("附件邮件发送成功！");
        } catch (MailException e) {
            logger.error("附件邮件发送失败！", e);
        }
    }

    /*
     * 发送多个附件邮件
     * to：发送给谁
     * subject：发送的主题（邮件主题）
     * content：发送的内容
     * filePath：附件路径
     * */
    public void sendAttachmentsMails(String to, String subject, String content, ArrayList filePaths) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content);

        //获取文件路径以及文件名称

        for (Object filepath : filePaths) {
            FileSystemResource fileSystemResource = new FileSystemResource(new File((String) filepath));
            String fileName = fileSystemResource.getFilename();

            mimeMessageHelper.addAttachment(fileName, fileSystemResource);
        }
        mimeMessageHelper.setFrom(from);

        try {
            mailSender.send(mimeMessage);
            logger.info("附件邮件发送成功！");
        } catch (MailException e) {
            logger.error("附件邮件发送失败！", e);
        }
    }

    /*
     * 发送图片件
     * to：发送给谁
     * subject：发送的主题（邮件主题）
     * content：发送的内容
     * imagePath：图片路径
     * imageId：图片ID
     * */
    public void sendImage(String to, String subject, String content, String imagePath, String imageId) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);

        FileSystemResource fileSystemResource = new FileSystemResource(new File(imagePath));

        mimeMessageHelper.addInline(imageId, fileSystemResource);
        mimeMessageHelper.setFrom(from);

        try {
            logger.info("图片邮件发送成功!");
            mailSender.send(mimeMessage);
        } catch (MailException e) {
            logger.error("图片邮件发送失败！", e);
        }
    }
}

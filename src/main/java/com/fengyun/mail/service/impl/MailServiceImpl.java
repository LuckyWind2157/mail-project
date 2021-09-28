package com.fengyun.mail.service.impl;

import cn.hutool.core.date.DateUtil;
import com.fengyun.mail.entity.MailProtocolDo;
import com.fengyun.mail.entity.SendDo;
import com.fengyun.mail.repository.MailProtocolRepository;
import com.fengyun.mail.repository.SendRepository;
import com.fengyun.mail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * @author Administrator
 */
@Service
public class MailServiceImpl implements MailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MailProtocolRepository mailProtocolRepository;
    private final SendRepository sendRepository;

    public MailServiceImpl(MailProtocolRepository mailProtocolRepository, SendRepository sendRepository) {
        this.mailProtocolRepository = mailProtocolRepository;
        this.sendRepository = sendRepository;
    }

    /**
     * 发送简单文本邮件
     * to：发送给谁
     * subject：发送的主题（邮件主题）
     * content：发送的内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content, Long userId) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        //发送邮件
        try {
            MailProtocolDo protocolDo = mailProtocolRepository.findByUserId(userId);
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setUsername(protocolDo.getUserName());
            mailSender.setPassword(protocolDo.getPassWord());
            mailSender.setHost(protocolDo.getSenderHost());
            mailSender.setPort(protocolDo.getSenderPort());
            mailSender.send(simpleMailMessage);
            SendDo sendDo = new SendDo();
            sendDo.setSubject(subject);
            sendDo.setSentDate(DateUtil.date());
            sendDo.setReceiveAddress(to);
            sendDo.setContent(content);
            sendRepository.save(sendDo);
            logger.info("简单邮件发送成功");
        } catch (MailException e) {
            logger.error("简单邮件发送失败", e);
        }
    }

    /**
     * 发送HTML邮件
     * to：发送给谁
     * subject：发送的主题（邮件主题）
     * content：发送的内容
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content, Long userId) throws MessagingException {
        MailProtocolDo protocolDo = mailProtocolRepository.findByUserId(userId);
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(protocolDo.getUserName());
        mailSender.setPassword(protocolDo.getPassWord());
        mailSender.setHost(protocolDo.getSenderHost());
        mailSender.setPort(protocolDo.getSenderPort());
        MimeMessage mimeMailMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content, true);
        try {
            mailSender.send(mimeMailMessage);
            logger.info("HTML邮件发送成功！");
        } catch (MailException e) {
            logger.error("HTML邮件发送失败！", e);
        }
    }


}

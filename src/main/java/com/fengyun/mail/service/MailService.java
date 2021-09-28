package com.fengyun.mail.service;

import javax.mail.MessagingException;

/**
 * @author Administrator
 */
public interface MailService {

    void sendSimpleMail(String to, String subject, String content, Long userId);

    void sendHtmlMail(String to, String subject, String content, Long userId) throws MessagingException;
}

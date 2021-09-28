package com.fengyun.mail.service;

import com.fengyun.mail.dto.MailDTO;
import com.fengyun.mail.dto.ResponsePageDTO;

import java.util.List;

/**
 * @author Administrator
 */
public interface MailService {

    void sendSimpleMail(String to, String subject, String content, Long userId);

    void sendHtmlMail(String to, String subject, String content, Long userId);

    ResponsePageDTO<List<MailDTO>> findByPage(Integer page, Integer size, MailDTO mailDTO);
}

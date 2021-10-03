package com.fengyun.mail.service.impl;

import cn.hutool.core.date.DateUtil;
import com.fengyun.mail.convert.ReceiverConverter;
import com.fengyun.mail.dto.MailDTO;
import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.entity.MailProtocolDo;
import com.fengyun.mail.entity.SendDo;
import com.fengyun.mail.repository.MailProtocolRepository;
import com.fengyun.mail.repository.SendRepository;
import com.fengyun.mail.service.MailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


/**
 * @author chenfengyun
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
     * 邮件发送
     * to：收件人
     * subject：邮件主题
     * content：邮件内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content, Long userId) {
        //发送邮件
        try {
            MailProtocolDo protocolDo = mailProtocolRepository.findByUserId(userId);
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setUsername(protocolDo.getUserName());
            mailSender.setPassword(protocolDo.getPassWord());
            mailSender.setHost(protocolDo.getSenderHost());
            mailSender.setPort(protocolDo.getSenderPort());
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(content);
            simpleMailMessage.setFrom(protocolDo.getUserName());
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
    public void sendHtmlMail(String to, String subject, String content, Long userId) {
        try {
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
            mimeMessageHelper.setFrom(protocolDo.getUserName());
            mailSender.send(mimeMailMessage);
            SendDo sendDo = new SendDo();
            sendDo.setSubject(subject);
            sendDo.setSentDate(DateUtil.date());
            sendDo.setReceiveAddress(to);
            sendDo.setContent(content);
            sendDo.setSendFrom(protocolDo.getUserName());
            sendRepository.save(sendDo);
            logger.info("HTML邮件发送成功！");
        } catch (Exception e) {
            logger.error("HTML邮件发送失败！", e);
        }
    }

    /**
     * 发件箱查询
     *
     * @param page    页数
     * @param size    每页条数
     * @param mailDTO 查询条件
     * @return 发送的邮件记录
     */
    @Override
    public ResponsePageDTO<List<MailDTO>> findByPage(Integer page, Integer size, MailDTO mailDTO) {
        Page<SendDo> pageDo = sendRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(mailDTO.getSubject())) {
                predicates.add(criteriaBuilder.equal(root.get("subject").as(String.class), mailDTO.getSubject()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page - 1, size, Sort.by("createdTime").descending()));

        ResponsePageDTO<List<MailDTO>> dto = new ResponsePageDTO<>();
        dto.setCount(pageDo.getTotalElements());
        dto.setData(ReceiverConverter.INSTANCE.doToSendDTO(pageDo.getContent()));
        return dto;
    }


}

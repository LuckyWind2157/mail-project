package com.fengyun.mail.service.impl;

import com.fengyun.mail.convert.ReceiverConverter;
import com.fengyun.mail.dto.MailDTO;
import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.entity.AttachmentFileDo;
import com.fengyun.mail.entity.MailProtocolDo;
import com.fengyun.mail.entity.ReceiverDo;
import com.fengyun.mail.enums.StatusEnum;
import com.fengyun.mail.repository.AttachmentFileRepository;
import com.fengyun.mail.repository.ReceiverRepository;
import com.fengyun.mail.service.MailProtocolService;
import com.fengyun.mail.service.ReceiverService;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author chenfengyun
 */
@Service
public class ReceiverServiceImpl implements ReceiverService {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ReceiverRepository receiverRepository;
    private final MailProtocolService mailProtocolService;
    private final AttachmentFileRepository attachmentFileRepository;
    private final RedisTemplate redisTemplate;

    public ReceiverServiceImpl(ReceiverRepository receiverRepository, MailProtocolService mailProtocolService, AttachmentFileRepository attachmentFileRepository, RedisTemplate redisTemplate) {
        this.receiverRepository = receiverRepository;
        this.mailProtocolService = mailProtocolService;
        this.attachmentFileRepository = attachmentFileRepository;
        this.redisTemplate = redisTemplate;
    }

    private void receiverMail(MailProtocolDo mailProtocolDo) {
        if (!redisTemplate.opsForValue().setIfAbsent(mailProtocolDo.getId(), "doing", Duration.ofHours(2))) return;
        if ("imap".equalsIgnoreCase(mailProtocolDo.getReceiverProtocol())) {
            try {
                // 准备连接服务器的会话信息
                Properties props = new Properties();
                props.setProperty("mail.store.protocol", mailProtocolDo.getReceiverProtocol());
                props.setProperty("mail.imap.host", mailProtocolDo.getReceiverHost());
                props.setProperty("mail.imap.port", mailProtocolDo.getReceiverPort().toString());
                // 创建Session实例对象
                Session session = Session.getInstance(props);
                // 创建IMAP协议的Store对象
                Store store = session.getStore("imap");
                // 连接邮件服务器
                store.connect(mailProtocolDo.getUserName(), mailProtocolDo.getPassWord());
                // 获得收件箱
                Folder folder = store.getFolder("INBOX");
                // 以读写模式打开收件箱
                folder.open(Folder.READ_WRITE);
                // 获得收件箱的邮件列表
                List<Message> messages = Arrays.asList(folder.getMessages());
                // 打印不同状态的邮件数量
                logger.info("收件箱中共" + messages.size() + "封邮件!");
                logger.info("收件箱中共" + folder.getUnreadMessageCount() + "封未读邮件!");
                logger.info("收件箱中共" + folder.getNewMessageCount() + "封新邮件!");
                logger.info("收件箱中共" + folder.getDeletedMessageCount() + "封已删除邮件!");
                logger.info("-------开始解析邮件-------");
                parseMessage(mailProtocolDo, messages);
                logger.info("-------结束解析邮件-------");
                // 关闭资源
                folder.close(true);
                store.close();
            } catch (Exception e) {
                logger.error("收取邮件异常：{}", mailProtocolDo, e);
            }
        }

    }

    @Override
    public void receiverMail() {
        mailProtocolService.findAllByStatus(StatusEnum.EFFECTIVE.getCode()).forEach(this::receiverMail);
    }

    @Override
    public ResponsePageDTO<List<MailDTO>> findByPage(Integer page, Integer size, MailDTO mailDTO) {
        Page<ReceiverDo> pageDo = receiverRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(mailDTO.getSubject())) {
                predicates.add(criteriaBuilder.equal(root.get("subject").as(String.class), mailDTO.getSubject()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page - 1, size, Sort.by("createdTime").descending()));

        ResponsePageDTO<List<MailDTO>> dto = new ResponsePageDTO<>();
        dto.setCount(pageDo.getTotalElements());
        dto.setData(ReceiverConverter.INSTANCE.doToDTO(pageDo.getContent()));
        return dto;
    }

    /**
     * 解析邮件
     */
    public void parseMessage(MailProtocolDo mailProtocolDo, List<Message> messages) {
        // 解析所有邮件
        messages.parallelStream().filter(v -> !receiverRepository.existsByMessageNumber(v.getMessageNumber())).forEach(v -> {
            try {
                MimeMessage msg = (MimeMessage) v;
                int messageNumber = msg.getMessageNumber();
                String subject = MimeUtility.decodeText(msg.getSubject());
                String sendFrom = getFrom(msg);
                String receiveAddress = getReceiveAddress(msg, null);
                Date sentDate = msg.getSentDate();
                boolean isSeen = isSeen(msg);
                String priority = getPriority(msg);
                boolean isReplySign = isReplySign(msg);
                boolean isContainerAttachment = isContainAttachment(msg);
                StringBuffer content = new StringBuffer(30);
                getMailTextContent(msg, content);
                logger.info("------------------解析第{}封邮件开始-------------------- ", messageNumber);
                logger.info("主题: {}", subject);
                logger.info("发件人:{} ", sendFrom);
                logger.info("收件人：{}", receiveAddress);
                logger.info("发送时间：{}", sentDate);
                logger.info("是否已读：{}", isSeen);
                logger.info("邮件优先级：{}", priority);
                logger.info("是否需要回执：{}", isReplySign);
                logger.info("邮件大小：{}{}", msg.getSize() * 1024, "kb");
                logger.info("是否包含附件：{}", isContainerAttachment);
                logger.info("------------------解析第{}封邮件结束-------------------- ", messageNumber);
                ReceiverDo receiverDo = new ReceiverDo();
                receiverDo.setMessageNumber(messageNumber);
                receiverDo.setSubject(subject);
                receiverDo.setSendFrom(sendFrom);
                receiverDo.setReceiveAddress(receiveAddress);
                receiverDo.setSentDate(sentDate);
                receiverDo.setIsSeen(isSeen ? StatusEnum.EFFECTIVE.getCode() : StatusEnum.NOT_EFFECTIVE.getCode());
                receiverDo.setPriority(priority);
                receiverDo.setIsReplySign(isReplySign ? StatusEnum.EFFECTIVE.getCode() : StatusEnum.NOT_EFFECTIVE.getCode());
                Set<AttachmentFileDo> attachmentFileDos = Sets.newHashSet();
                if (isContainerAttachment) {
                    attachmentFileDos = getAttachment(msg);
                }
                receiverDo.setAttachmentFileSet(attachmentFileDos);
                receiverDo.setContent(content.toString());
                receiverDo.setUserId(mailProtocolDo.getUserId());
                attachmentFileRepository.saveAll(attachmentFileDos);
                receiverRepository.save(receiverDo);
            } catch (Exception e) {
                logger.error("邮件解析失败", e);
            }
        });
    }

    private Set<AttachmentFileDo> getAttachment(Part part) {
        Set<AttachmentFileDo> set = Sets.newHashSet();
        try {
            if (part.isMimeType("multipart/*")) {
                //复杂体邮件
                Multipart multipart = (Multipart) part.getContent();
                //复杂体邮件包含多个邮件体
                int partCount = multipart.getCount();
                for (int i = 0; i < partCount; i++) {
                    //获得复杂体邮件中其中一个邮件体
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    //某一个邮件体也有可能是由多个邮件体组成的复杂体
                    String disp = bodyPart.getDisposition();
                    if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                        AttachmentFileDo attachmentFileDo = new AttachmentFileDo();
                        attachmentFileDo.setFileName(decodeText(bodyPart.getFileName()));
                        attachmentFileDo.setFile(bodyPart.getInputStream().readAllBytes());
                        set.add(attachmentFileDo);
                    } else if (bodyPart.isMimeType("multipart/*")) {
                        set.addAll(getAttachment(bodyPart));
                    } else {
                        String contentType = bodyPart.getContentType();
                        if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {
                            AttachmentFileDo attachmentFileDo = new AttachmentFileDo();
                            attachmentFileDo.setFileName(decodeText(bodyPart.getFileName()));
                            attachmentFileDo.setFile(bodyPart.getInputStream().readAllBytes());
                            set.add(attachmentFileDo);
                        }
                    }
                }
            } else if (part.isMimeType("message/rfc822")) {
                set.addAll(getAttachment(part));
            }
        } catch (Exception e) {
            logger.error("邮件附件解析失败", e);
        }
        return set;
    }


    /**
     * 获得邮件发件人
     *
     * @param msg 邮件内容
     * @return 姓名 <Email地址>
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public static String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
        String from = "";
        Address[] froms = msg.getFrom();
        if (froms.length < 1)
            throw new MessagingException("没有发件人!");
        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();
        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }
        from = person + "<" + address.getAddress() + ">";

        return from;
    }

    /**
     * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
     * <p>Message.RecipientType.TO  收件人</p>
     * <p>Message.RecipientType.CC  抄送</p>
     * <p>Message.RecipientType.BCC 密送</p>
     *
     * @param msg  邮件内容
     * @param type 收件人类型
     * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
     * @throws MessagingException
     */
    public static String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
        StringBuffer receiveAddress = new StringBuffer();
        Address[] addresss = null;
        if (type == null) {
            addresss = msg.getAllRecipients();
        } else {
            addresss = msg.getRecipients(type);
        }

        if (addresss == null || addresss.length < 1)
            throw new MessagingException("没有收件人!");
        for (Address address : addresss) {
            InternetAddress internetAddress = (InternetAddress) address;
            receiveAddress.append(internetAddress.toUnicodeString()).append(",");
        }

        receiveAddress.deleteCharAt(receiveAddress.length() - 1);    //删除最后一个逗号

        return receiveAddress.toString();
    }


    /**
     * 判断邮件中是否包含附件
     *
     * @param part 邮件内容
     * @return 邮件中存在附件返回true，不存在返回false
     * @throws MessagingException
     * @throws IOException
     */
    public boolean isContainAttachment(Part part) throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = isContainAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.indexOf("application") != -1) {
                        flag = true;
                    }

                    if (contentType.indexOf("name") != -1) {
                        flag = true;
                    }
                }

                if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = isContainAttachment((Part) part.getContent());
        }
        return flag;
    }

    /**
     * 判断邮件是否已读
     *
     * @param msg 邮件内容
     * @return 如果邮件已读返回true, 否则返回false
     * @throws MessagingException
     */
    public boolean isSeen(MimeMessage msg) {
        boolean flag = false;
        try {
            flag = msg.getFlags().contains(Flags.Flag.SEEN);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 判断邮件是否需要阅读回执
     *
     * @param msg 邮件内容
     * @return 需要回执返回true, 否则返回false
     * @throws MessagingException
     */
    public boolean isReplySign(MimeMessage msg) throws MessagingException {
        boolean replySign = false;
        String[] headers = msg.getHeader("Disposition-Notification-To");
        if (headers != null)
            replySign = true;
        return replySign;
    }

    /**
     * 获得邮件的优先级
     *
     * @param msg 邮件
     * @return 邮件登记
     * @throws Exception
     */
    public String getPriority(MimeMessage msg) throws Exception {
        String priority = "普通";
        String[] headers = msg.getHeader("X-Priority");
        if (headers != null) {
            String headerPriority = headers[0];
            if (headerPriority.indexOf("1") != -1 || headerPriority.indexOf("High") != -1)
                priority = "紧急";
            else if (headerPriority.indexOf("5") != -1 || headerPriority.indexOf("Low") != -1)
                priority = "低";
            else
                priority = "普通";
        }
        return priority;
    }

    /**
     * 获得邮件文本内容
     *
     * @param part    邮件体
     * @param content 存储邮件文本内容的字符串
     * @throws Exception
     */
    public void getMailTextContent(Part part, StringBuffer content) throws Exception {
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            content.append(part.getContent().toString());
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part) part.getContent(), content);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart, content);
            }
        }
    }

    /**
     * 文本解码
     *
     * @param encodeText 解码MimeUtility.encodeText(String text)方法编码后的文本
     * @return 解码后的文本
     * @throws UnsupportedEncodingException
     */
    public String decodeText(String encodeText) throws UnsupportedEncodingException {
        if (encodeText == null || "".equals(encodeText)) {
            return "";
        } else {
            return MimeUtility.decodeText(encodeText);
        }
    }

}

package com.fengyun.mail.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_receiver")
@Data
public class ReceiverDo extends BaseDo implements Serializable {
    private static final long serialVersionUID = -4097530692144006362L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    //主题
    private String subject;
    //发件人
    private String sendFrom;
    //收件人
    private String receiveAddress;
    //发送时间
    private Date sentDate;
    //是否已读
    private String isSeen;
    //邮件优先级
    private String priority;
    //邮件正文
    @Column(name = "content", columnDefinition = "longtext")
    private String content;
    // 邮件编号
    private Integer messageNumber;
    //是否需要回执
    private String isReplySign;

    @OneToMany(mappedBy = "receiverDo")
    @JsonBackReference
    private Set<AttachmentFileDo> AttachmentFileSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "Protocol_id")
    private MailProtocolDo mailProtocolDo;
}

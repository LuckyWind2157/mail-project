package com.fengyun.mail.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_send")
@Data
public class SendDo extends BaseDo implements Serializable {
    private static final long serialVersionUID = -4097530692144006362L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Long userId;
    //主题
    private String subject;
    //发件人
    private String sendFrom;
    //收件人
    private String receiveAddress;
    //发送时间
    private Date sentDate;
    //邮件正文
    @Column(name = "content", columnDefinition = "longtext")
    private String content;

    @OneToMany(mappedBy = "receiverDo")
    @JsonBackReference
    private Set<AttachmentFileDo> AttachmentFileSet;

}

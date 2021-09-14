package com.fengyun.mail.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "t_receiver")
@Data
public class ReceiverDao extends BaseDao implements Serializable {
    private static final long serialVersionUID = -4097530692144006362L;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    //主题
    private String subject;
    //发件人
    private String From;
    //收件人
    private String receiveAddress;
    //发送时间
    private Date sentDate;
    //是否已读
    private Boolean isSeen;
    //邮件优先级
    private String priority;
    //是否需要回执
    private Boolean isReplySign;

    @OneToMany(mappedBy = "receiverDao")
    @JsonBackReference
    private Set<AttachmentFileDao> AttachmentFileSet;
}

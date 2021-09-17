package com.fengyun.mail.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Table(name = "t_mail_protocol")
@Entity
@Data
public class MailProtocolDo extends BaseDo implements Serializable {
    private static final long serialVersionUID = 5282967255237973786L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String receiverProtocol;
    private String receiverHost;
    private String receiverPort;
    private String userName;
    private String password;
    private String senderHost;
    private String senderPort;

//    @OneToMany(mappedBy = "mailProtocolDo")
//    @JsonBackReference
//    private Set<ReceiverDo> receiverDos;


}

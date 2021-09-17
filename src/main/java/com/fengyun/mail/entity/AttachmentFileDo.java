package com.fengyun.mail.entity;

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
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_attachment_file")
@Data
public class AttachmentFileDo extends BaseDo implements Serializable {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name = "receiver_id")
    private ReceiverDo receiverDo;
    @Column(columnDefinition = "longBlob ")
    private byte[] file;
}

package com.fengyun.mail.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseDo {
    private Long createdId;
    @CreatedDate
    @Column(updatable = false)
    private Date createdTime;
    private Long updatedId;
    @LastModifiedDate
    private Date updatedTime;
    private String status;


}

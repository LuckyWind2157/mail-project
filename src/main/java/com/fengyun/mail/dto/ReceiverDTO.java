package com.fengyun.mail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ReceiverDTO implements Serializable {

    private static final long serialVersionUID = -7076428364752333348L;
    private Long id;
    //主题
    private String subject;
    //发件人
    private String sendFrom;
    //收件人
    private String receiveAddress;
    //发送时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sentDate;
    //是否已读
    private String isSeen;

    //邮件正文
    private String content;


}

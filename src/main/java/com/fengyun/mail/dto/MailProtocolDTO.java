package com.fengyun.mail.dto;


import com.fengyun.mail.entity.BaseDo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenfengyun
 */

@Data
public class MailProtocolDTO extends BaseDo implements Serializable {

    private Long id;
    private Long userId;
    private String receiverProtocol;
    private String receiverHost;
    private String receiverPort;
    private String userName;
    private String passWord;
    private String senderHost;
    private String senderPort;


}

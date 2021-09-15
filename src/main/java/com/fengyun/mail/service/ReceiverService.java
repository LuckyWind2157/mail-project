package com.fengyun.mail.service;

import com.fengyun.mail.entity.MailProtocolDo;

public interface ReceiverService {
    void receiverMail(MailProtocolDo mailProtocolDo);

    void receiverMail();
}

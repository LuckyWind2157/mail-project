package com.fengyun.mail;

import com.fengyun.mail.service.MailProtocolService;
import com.fengyun.mail.service.ReceiverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ReceiverTest {

    @Autowired
    private ReceiverService receiverService;
    @Autowired
    private MailProtocolService mailProtocolService;

    /**
     * 测试邮件服务的收件功能
     */
    @Test
    public void test() {
        receiverService.receiverMail();
    }
}

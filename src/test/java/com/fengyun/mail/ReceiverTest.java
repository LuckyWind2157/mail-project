package com.fengyun.mail;

import com.fengyun.mail.entity.MailProtocolDo;
import com.fengyun.mail.enums.StatusEnum;
import com.fengyun.mail.service.MailProtocolService;
import com.fengyun.mail.service.ReceiverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReceiverTest {

    @Autowired
    private ReceiverService receiverService;
    @Autowired
    private MailProtocolService mailProtocolService;

    @Test
    public void test() {
        List<MailProtocolDo> list = mailProtocolService.findAllByStatus(StatusEnum.EFFECTIVE.getCode());
        list.forEach(v -> {
            try {
                receiverService.receiverMail(v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        ;
    }
}

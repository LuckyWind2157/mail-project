package com.fengyun.mail.Initialization;

import com.fengyun.mail.service.impl.ReceiverServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ReceiverInitializing implements InitializingBean {

    private final ReceiverServiceImpl receiverService;

    public ReceiverInitializing(ReceiverServiceImpl receiverService) {
        this.receiverService = receiverService;
    }

    @Override
    public void afterPropertiesSet() {
        log.info("用户信息缓存");
    }


}

package com.fengyun.mail;

import com.fengyun.mail.service.ReceiverService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class ReceiverMailRunnerImpl implements ApplicationRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    private final ReceiverService receiverService;
    private final ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(10, new BasicThreadFactory.Builder().namingPattern("receiver-mail-runner-pool-%d").daemon(true).build());

    public ReceiverMailRunnerImpl(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("邮件接收任务开始启动");
        scheduledExecutorService.scheduleAtFixedRate((Runnable) receiverService::receiverMail, 0, 1, TimeUnit.MINUTES);
        logger.info("邮件接收任务启动成功");
    }
}

package com.fengyun.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author chenfengyun
 */
@SpringBootApplication
@EnableJpaAuditing
public class MailProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailProjectApplication.class, args);
    }

}

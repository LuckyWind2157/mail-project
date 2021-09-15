package com.fengyun.mail.service.impl;

import com.fengyun.mail.entity.MailProtocolDo;
import com.fengyun.mail.repository.MailProtocolRepository;
import com.fengyun.mail.service.MailProtocolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailProtocolServiceImpl implements MailProtocolService {


    private final MailProtocolRepository mailProtocolRepository;

    public MailProtocolServiceImpl(MailProtocolRepository mailProtocolRepository) {
        this.mailProtocolRepository = mailProtocolRepository;
    }

    @Override
    public List<MailProtocolDo> findAllByStatus(String status) {
        return mailProtocolRepository.findAllByStatus(status);
    }
}

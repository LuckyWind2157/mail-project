package com.fengyun.mail.service;

import com.fengyun.mail.entity.MailProtocolDo;

import java.util.List;

public interface MailProtocolService {
    List<MailProtocolDo> findAllByStatus(String code);
}

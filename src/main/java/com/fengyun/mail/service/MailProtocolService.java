package com.fengyun.mail.service;

import com.fengyun.mail.dto.MailProtocolDTO;
import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.entity.MailProtocolDo;

import java.util.List;

public interface MailProtocolService {
    List<MailProtocolDo> findAllByStatus(String code);

    MailProtocolDTO findByUserId(Long userId);

    void saveOrUpdate(MailProtocolDTO dto);

}

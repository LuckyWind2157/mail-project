package com.fengyun.mail.service;

import com.fengyun.mail.dto.MailDTO;
import com.fengyun.mail.dto.ResponsePageDTO;

import java.util.List;

public interface ReceiverService {
    void receiverMail();

    ResponsePageDTO<List<MailDTO>> findByPage(Integer page, Integer size, MailDTO mailDTO);
}

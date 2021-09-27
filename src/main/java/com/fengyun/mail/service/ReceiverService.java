package com.fengyun.mail.service;

import com.fengyun.mail.dto.ReceiverDTO;
import com.fengyun.mail.dto.ResponsePageDTO;

import java.util.List;

public interface ReceiverService {
    void receiverMail();

    ResponsePageDTO<List<ReceiverDTO>> findByPage(Integer page, Integer size, ReceiverDTO receiverDTO);
}

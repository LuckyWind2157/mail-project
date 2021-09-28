package com.fengyun.mail.controller;

import com.fengyun.mail.dto.MailDTO;
import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.service.ReceiverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("re/")
public class ReceiverController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ReceiverService receiverService;


    public ReceiverController(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }


    @GetMapping("findByPage")
    public ResponsePageDTO<List<MailDTO>> getList(@RequestParam("page") Integer page, @RequestParam("limit") Integer size, MailDTO mailDTO) {
        return receiverService.findByPage(page, size, mailDTO);
    }

}

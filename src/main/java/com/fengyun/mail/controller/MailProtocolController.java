package com.fengyun.mail.controller;

import com.auth0.jwt.JWT;
import com.fengyun.mail.dto.MailProtocolDTO;
import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.service.MailProtocolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chenfengyun
 */

@RestController
@RequestMapping("protocol")
public class MailProtocolController {

    private final MailProtocolService mailProtocolService;

    public MailProtocolController(MailProtocolService mailProtocolService) {
        this.mailProtocolService = mailProtocolService;
    }

    @RequestMapping("/findByToken")
    public ResponsePageDTO<MailProtocolDTO> findByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JWT.decode(token).getClaim("userId").asLong();
        MailProtocolDTO mailProtocolDTO = mailProtocolService.findByUserId(userId);
        return ResponsePageDTO.ok(mailProtocolDTO);
    }

    @PostMapping("/save")
    public ResponsePageDTO<Void> saveOrUpdate(HttpServletRequest request, MailProtocolDTO dto) {
        String token = request.getHeader("token");
        Long userId = JWT.decode(token).getClaim("userId").asLong();
        dto.setUpdatedId(userId);
        mailProtocolService.saveOrUpdate(dto);
        return ResponsePageDTO.ok();
    }

}

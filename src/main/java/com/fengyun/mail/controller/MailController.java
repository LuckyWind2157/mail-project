package com.fengyun.mail.controller;

import com.auth0.jwt.JWT;
import com.fengyun.mail.dto.MailDTO;
import com.fengyun.mail.dto.MailProtocolDTO;
import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.service.MailProtocolService;
import com.fengyun.mail.service.MailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 邮件发送和账号设置接口
 *
 * @author chenfengyun
 */

@RestController
@RequestMapping("mail")
public class MailController {

    private final MailProtocolService mailProtocolService;
    private final MailService mailService;

    public MailController(MailProtocolService mailProtocolService, MailService mailService) {
        this.mailProtocolService = mailProtocolService;
        this.mailService = mailService;
    }

    /**
     * 获取邮件账号配置
     *
     * @param request
     * @return
     */
    @RequestMapping("/findByToken")
    public ResponsePageDTO<MailProtocolDTO> findByToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        Long userId = JWT.decode(token).getClaim("userId").asLong();
        MailProtocolDTO mailProtocolDTO = mailProtocolService.findByUserId(userId);
        return ResponsePageDTO.ok(mailProtocolDTO);
    }

    /**
     * 保存后者修改邮件账号配置
     *
     * @param request
     * @param dto
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public ResponsePageDTO<Void> saveOrUpdate(HttpServletRequest request, MailProtocolDTO dto) {
        String token = request.getHeader("token");
        Long userId = JWT.decode(token).getClaim("userId").asLong();
        dto.setUserId(userId);
        dto.setUpdatedId(userId);
        mailProtocolService.saveOrUpdate(dto);
        return ResponsePageDTO.ok();
    }

    /**
     * 保存后者修改邮件账号配置
     *
     * @param request
     * @param dto
     * @return
     */
    @PostMapping("/send")
    public ResponsePageDTO<Void> send(HttpServletRequest request, MailDTO dto) {
        String token = request.getHeader("token");
        Long userId = JWT.decode(token).getClaim("userId").asLong();
        mailService.sendSimpleMail(dto.getReceiveAddress(), dto.getSubject(), dto.getContent(), userId);
        return ResponsePageDTO.ok();
    }
}

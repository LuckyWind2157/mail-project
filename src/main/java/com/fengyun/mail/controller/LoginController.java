package com.fengyun.mail.controller;

import cn.hutool.crypto.digest.MD5;
import com.fengyun.mail.dto.UserDTO;
import com.fengyun.mail.service.UserService;
import com.fengyun.mail.service.impl.JwtServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author chenfengyun
 */
@RestController
@RequestMapping("/login")
public class LoginController {


    private final UserService userService;
    private final JwtServiceImpl jwtServicewtServiceImpl;


    public LoginController(UserService userService, JwtServiceImpl jwtServicewtServiceImpl) {
        this.userService = userService;
        this.jwtServicewtServiceImpl = jwtServicewtServiceImpl;
    }

    @RequestMapping("")
    public String login(HttpServletResponse response, UserDTO userDTO) {
        //明文密码MD5加密
        userDTO.setPassword(MD5.create().digestHex(userDTO.getPassword()));
        userDTO = userService.findOne(userDTO);
        if (Objects.isNull(userDTO)) {
            throw new RuntimeException("用户不存在或者密码错误");
        }
        // 创建token
        String token = jwtServicewtServiceImpl.sign(userDTO);
        // 将token放在响应头
        response.setHeader("token", token);
        return token;
    }
}

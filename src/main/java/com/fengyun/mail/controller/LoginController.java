package com.fengyun.mail.controller;

import javax.servlet.http.HttpServletResponse;

public class LoginController {


    public String login(HttpServletResponse response, TbUser tbUser) {


        //明文密码MD5加密
        tbUser.setUserPassword(StrToMd5.Md5(tbUser.getUserPassword()));
        TbUser user = tbUserDao.login(tbUser);

        if (user == null) {
            throw new SystemException(ResultEnum.LOGIN_ERROR);
        }
        // 创建token
        String token = JwtTokenUtil.createJWT(user.getId(), user.getUserName(), "user_role");
        // 将token放在响应头
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, JwtTokenUtil.TOKEN_PREFIX + token);
        redisService.set(token, user.getId());
        return token;
    }
}

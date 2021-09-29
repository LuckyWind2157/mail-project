package com.fengyun.mail.config;

import com.fengyun.mail.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenfengyun
 */
@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JwtService jwtService;

    public AuthenticationInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        String[] uri = httpServletRequest.getRequestURL().toString().split("/");
        String redirectURL = uri[0] + "//" + uri[2] + "/";
        // 执行认证
        if (token == null) {
            logger.info("无token，请重新登录");
            return false;
        }
        return jwtService.verity(token);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}

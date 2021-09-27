package com.fengyun.mail.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.fengyun.mail.dto.UserDTO;
import com.fengyun.mail.service.JwtService;
import com.fengyun.mail.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;

/**
 * @author chenfengyun
 */
@Service
public class JwtServiceImpl implements JwtService {


    private final UserService userService;
    private final RedisTemplate redisTemplate;


    /**
     * 过期时间
     */
    private static final long EXPIRE_TIME = 15 * 60 * 1000;


    public JwtServiceImpl(UserService userService, RedisTemplate redisTemplate) {
        this.userService = userService;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 生成签名,15分钟后过期
     */
    @Override
    public String sign(UserDTO userDTO) {
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(userDTO.getPassword());
        //设置头信息
        HashMap<String, Object> header = new HashMap<>(2);
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        //附带username和userID生成签名
        String token = JWT.create().withHeader(header).withClaim("loginName", userDTO.getUserName()).withClaim("userId", userDTO.getId()).withExpiresAt(date).sign(algorithm);
        redisTemplate.opsForHash().put("login", token, userDTO);
        return token;
    }

    @Override
    public boolean verity(String token) {
        try {
            UserDTO userDTO = (UserDTO) redisTemplate.opsForHash().get("login", token);
            Algorithm algorithm = Algorithm.HMAC256(userDTO.getPassword());
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}

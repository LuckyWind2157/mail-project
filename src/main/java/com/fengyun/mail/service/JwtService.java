package com.fengyun.mail.service;

import com.fengyun.mail.dto.UserDTO;

/**
 * @author chenfengyun
 */
public interface JwtService {
    /**
     * 生成token
     *
     * @param userDTO 用户信息
     * @return token
     */
    String sign(UserDTO userDTO);

    /**
     * 校验token
     *
     * @param token token
     * @return 结果
     */
    boolean verity(String token);
}

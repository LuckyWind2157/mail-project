package com.fengyun.mail.Initialization;

import com.fengyun.mail.domain.UserDao;
import com.fengyun.mail.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
@Slf4j
public class TbUserMap implements InitializingBean {


    private Map<Long, UserDao> tbUserMap = new ConcurrentHashMap<>();

    private final UserService userService;

    public TbUserMap(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("用户信息缓存");
       // Map<Long, UserDao> userMap = userService.findAll().stream().collect(Collectors.toMap(UserDao::getId, v -> v));
        //tbUserMap.putAll(userMap);
    }

    public UserDao get(Long id) {
        return tbUserMap.get(id);
    }

    public void remove(Long id) {
        tbUserMap.remove(id);
    }

    public void put(UserDao tbUser) {
        tbUserMap.put(tbUser.getId(), tbUser);
    }


}

package com.fengyun.mail.service.impl;

import com.fengyun.mail.entity.UserDao;
import com.fengyun.mail.repository.UserRepository;
import com.fengyun.mail.service.UserService;
import com.fengyun.mail.enums.StatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDao> findAll() {
        return userRepository.findAllByStatus(StatusEnum.EFFECTIVE.getCode());
    }
}

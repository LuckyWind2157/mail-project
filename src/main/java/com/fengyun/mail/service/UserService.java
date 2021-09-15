package com.fengyun.mail.service;

import com.fengyun.mail.entity.UserDao;

import java.util.List;

public interface UserService {
    List<UserDao> findAll();
}

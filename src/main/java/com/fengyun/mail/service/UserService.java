package com.fengyun.mail.service;

import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.dto.UserDTO;

import java.util.List;

public interface UserService {
    UserDTO findById(Long id);

    ResponsePageDTO<List<UserDTO>> findByPage(Integer page, Integer size, String sort, UserDTO userDTO);

    void saveOrUpdate(UserDTO userDTO);

    UserDTO findOne(UserDTO userDTO);
}

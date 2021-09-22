package com.fengyun.mail.service;

import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.dto.RoleDTO;

import java.util.List;

public interface RoleService {
    ResponsePageDTO<List<RoleDTO>> findByPage(Integer page, Integer size, String sort, RoleDTO roleDTO);

    void saveOrUpdate(RoleDTO roleDTO);
}

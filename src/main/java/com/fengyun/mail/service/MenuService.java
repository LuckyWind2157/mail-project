package com.fengyun.mail.service;

import com.fengyun.mail.dto.MenuDTO;
import com.fengyun.mail.dto.ResponsePageDTO;

import java.util.List;

public interface MenuService {


    ResponsePageDTO<List<MenuDTO>> findByPage(Integer page, Integer size, String sort, MenuDTO menuDTO);

    void saveOrUpdate(MenuDTO menuDTO);
}

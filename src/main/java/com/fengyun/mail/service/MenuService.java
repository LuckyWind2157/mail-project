package com.fengyun.mail.service;

import com.fengyun.mail.dto.MenuDTO;
import com.fengyun.mail.entity.MenuDo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {


    Page<MenuDo> findByPage(Page page, MenuDTO menuDTO);

    void saveOrUpdate(MenuDTO menuDTO);

    MenuDTO findById(long id);

    List<MenuDTO> findAllByStatus(String code);

    List<MenuDTO> findAllByName(String name);
}

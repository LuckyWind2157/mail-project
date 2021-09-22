package com.fengyun.mail.controller;

import com.fengyun.mail.dto.MenuDTO;
import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.enums.StatusEnum;
import com.fengyun.mail.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("menu")
public class MenuController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("findByPage")
    public ResponsePageDTO<List<MenuDTO>> getList(@RequestParam("page") Integer page, @RequestParam("limit") Integer size, String sort, MenuDTO menuDTO) {
        return menuService.findByPage(page, size, sort, menuDTO);
    }

    @PostMapping("/saveOrUpdate")
    public ResponsePageDTO<Void> saveOrUpdate(MenuDTO menuDTO) {
        logger.info("saveOrUpdate");
        menuService.saveOrUpdate(menuDTO);
        return ResponsePageDTO.ok();
    }

    @DeleteMapping("/del")
    public ResponsePageDTO<Void> delete(Long id) {
        logger.info("del");
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(id);
        menuDTO.setStatus(StatusEnum.NOT_EFFECTIVE.getCode());
        menuService.saveOrUpdate(menuDTO);
        return ResponsePageDTO.ok();
    }

}

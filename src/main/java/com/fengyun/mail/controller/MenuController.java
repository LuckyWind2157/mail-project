package com.fengyun.mail.controller;

import com.fengyun.mail.dto.MenuDTO;
import com.fengyun.mail.dto.Response;
import com.fengyun.mail.enums.StatusEnum;
import com.fengyun.mail.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Response<MenuDTO> selectOne(long id) {
        return Response.ok(this.menuService.findById(id));
    }

    @GetMapping("getList")
    public Response<List<MenuDTO>> getList() {
        logger.info("获取菜单列表");
        List<MenuDTO> list = menuService.findAllByStatus(StatusEnum.EFFECTIVE.getCode());
        return Response.ok(list);
    }

    @PostMapping("/setMenu")
    public Response<Void> addMenu(MenuDTO menuDTO) {
        logger.info("新增菜单");
        menuService.saveOrUpdate(menuDTO);
        return Response.ok();
    }


    @GetMapping("/getMenuByName")
    public Response<List<MenuDTO>> getMenuByName(String name) {
        List<MenuDTO> list = menuService.findAllByName(name);
        return Response.ok(list);
    }

    @DeleteMapping("/delete")
    public Response<Void> deleteById(Long id) {
        logger.info("删除菜单");
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setId(id);
        menuDTO.setStatus(StatusEnum.NOT_EFFECTIVE.getCode());
        menuService.saveOrUpdate(menuDTO);
        return Response.ok();
    }

//    @PostMapping("/getPermission")
//    public ResultVO getPermission(String id) {
//        List<MenuTreeResult> list = tbMenuService.getMenusByRoleId(id);
//        return ResultVOUtil.success(list);
//    }
}

package com.fengyun.mail.controller;

import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.dto.RoleDTO;
import com.fengyun.mail.enums.StatusEnum;
import com.fengyun.mail.service.RoleService;
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
@RequestMapping("role")
public class RoleController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/findByPage")
    public ResponsePageDTO<List<RoleDTO>> getList(@RequestParam("page") Integer page, @RequestParam("limit") Integer size, String sort, RoleDTO roleDTO) {
        return roleService.findByPage(page, size, sort, roleDTO);
    }

    @PostMapping("/saveOrUpdate")
    public ResponsePageDTO<Void> saveOrUpdate(RoleDTO roleDTO) {
        logger.info("saveOrUpdate");
        roleService.saveOrUpdate(roleDTO);
        return ResponsePageDTO.ok();
    }

    @DeleteMapping("/del")
    public ResponsePageDTO<Void> delete(Long id) {
        logger.info("del");
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(id);
        roleDTO.setStatus(StatusEnum.NOT_EFFECTIVE.getCode());
        roleService.saveOrUpdate(roleDTO);
        return ResponsePageDTO.ok();
    }

}

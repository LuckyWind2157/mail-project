package com.fengyun.mail.controller;


import com.fengyun.mail.dto.ResponsePageDTO;
import com.fengyun.mail.dto.UserDTO;
import com.fengyun.mail.enums.StatusEnum;
import com.fengyun.mail.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponsePageDTO<UserDTO> getUser() {
        Long id = 1L;
        return ResponsePageDTO.ok(userService.findById(id));
    }

    @GetMapping("findByPage")
    public ResponsePageDTO<List<UserDTO>> getList(@RequestParam("page") Integer page, @RequestParam("limit") Integer size, String sort, UserDTO userDTO) {
        return userService.findByPage(page, size, sort, userDTO);
    }

    @PostMapping("/saveOrUpdate")
    public ResponsePageDTO<Void> saveOrUpdate(UserDTO userDTO) {
        logger.info("saveOrUpdate");
        userService.saveOrUpdate(userDTO);
        return ResponsePageDTO.ok();
    }

    @DeleteMapping("/del")
    public ResponsePageDTO<Void> delete(Long id) {
        logger.info("del");
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);
        userDTO.setStatus(StatusEnum.NOT_EFFECTIVE.getCode());
        userService.saveOrUpdate(userDTO);
        return ResponsePageDTO.ok();
    }
}

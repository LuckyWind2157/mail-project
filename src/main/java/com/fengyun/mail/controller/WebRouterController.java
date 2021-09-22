package com.fengyun.mail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/html")
public class WebRouterController {

    @RequestMapping("/user")
    public String getUser() {
        return "user/index";
    }

    @RequestMapping("/role")
    public String getRole() {
        return "role/roleIndex";
    }

    @RequestMapping("/menu")
    public String getMenu() {
        return "menu/menuIndex";
    }


}

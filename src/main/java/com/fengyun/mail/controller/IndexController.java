package com.fengyun.mail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chenfengyun
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String login() {
        return "view/login";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}

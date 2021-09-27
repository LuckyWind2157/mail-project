package com.fengyun.mail.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author chenfengyun
 */
@Controller
@RequestMapping("/html")
public class HtmlController {


    @RequestMapping("/user/{path}")
    public String userRouter(@PathVariable("path") String path) {
        String html = "page/user/" + path;
        return html;
    }

    @RequestMapping("/role/{path}")
    public String roleRouter(@PathVariable("path") String path) {
        String html = "page/user/" + path;
        return html;
    }

    @RequestMapping("/menu/{path}")
    public String menuRouter(@PathVariable("path") String path) {
        String html = "page/user/" + path;
        return html;
    }

    @RequestMapping("/404")
    public String errorPage() {
        return "page/404";
    }
}

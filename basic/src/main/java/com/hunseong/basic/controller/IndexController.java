package com.hunseong.basic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Hunseong on 2022/05/13
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String root() {
        return "index";
    }
}

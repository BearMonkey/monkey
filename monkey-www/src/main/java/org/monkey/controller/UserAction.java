package org.monkey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserAction {

    @RequestMapping("user/list")
    public String list(){
        System.out.println("here!");
        return "user/list";
    }
}

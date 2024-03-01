package org.monkey.user.controller;

import org.monkey.common.dto.Result;
import org.monkey.user.pojo.User;
import org.monkey.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login() {
        return null;
    }

    @PostMapping("/regist")
    public Result regist(@RequestBody User user) {
        userService.addUser(user);
        return new Result();
    }
}

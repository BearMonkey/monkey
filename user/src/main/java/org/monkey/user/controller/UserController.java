package org.monkey.user.controller;

import org.monkey.oauth.common.dto.Result;
import org.monkey.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public Result getAllUser() {
        return new Result(userService.getAllUser());
    }
}

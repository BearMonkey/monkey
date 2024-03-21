package org.monkey.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public Result getAllUser() {
        log.info("uri comming");
        return new Result(userService.getAllUser());
    }
}

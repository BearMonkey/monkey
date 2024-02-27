package org.monkey.auth.controller;

import org.monkey.oauth.common.dto.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/token")
    public Result getToken() {
        System.out.println("get token");
        return new Result("String");
    }
}

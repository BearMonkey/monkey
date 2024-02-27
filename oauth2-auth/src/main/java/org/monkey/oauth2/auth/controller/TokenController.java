package org.monkey.oauth2.auth.controller;

import org.monkey.oauth.common.dto.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/token")
public class TokenController {

    @PostMapping
    public Result getToken() {
        return new Result("00", "success", "this is token");
    }
}

package org.monkey.oauth.resource.controller;

import org.monkey.oauth.common.dto.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {
    @GetMapping("/info")
    public Result getResource() {
        return new Result("00", "success", "this is resource's info");
    }
}

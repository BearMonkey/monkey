package org.monkey.oauth2.app.controller;

import org.monkey.oauth.common.dto.Result;
import org.monkey.oauth2.app.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService appService;

    @GetMapping("/info")
    public Result getInfos() {
        String infos = appService.getInfos();
        return new Result("00", "success", infos);
    }
}

package org.monkey.oauth2.app.client;

import org.monkey.oauth.common.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "resource", path = "/resource") // name应该是微服务名称
public interface ResourceFeignClient {
    @GetMapping("/info")
    Result getResource();
}

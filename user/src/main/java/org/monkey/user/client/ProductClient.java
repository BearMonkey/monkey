package org.monkey.user.client;

import org.monkey.common.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "product", fallback = ProdutClientFallback.class)
public interface ProductClient {
    @GetMapping("/product")
    Result lsit();
}

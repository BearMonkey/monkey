package org.monkey.user.client;

import org.monkey.common.dto.Result;
import org.springframework.stereotype.Component;

@Component
public class ProdutClientFallback implements ProductClient{
    @Override
    public Result lsit() {
        return Result.error("方法调用失败");
    }
}

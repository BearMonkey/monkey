package org.monkey.dist.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.dist.order.dto.OrderInfo;
import org.monkey.dist.order.service.OrderService;
import org.monkey.common.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dist/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping
    public Result createOrder(@RequestBody OrderInfo orderInfo) {
        orderService.createOrder(orderInfo);
        return Result.ok();
    }

    @GetMapping
    public Result listOrder() {
        log.info("recieve req: /dist/order, list order:");
        return new Result(orderService.listOrder());
    }
}

package org.monkey.mqtx.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.mqtx.order.service.OrderService;
import org.monkey.mqtx.common.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtx/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("")
    public Result createOrder(@RequestBody OrderVo orderVo) {
        log.info("receive reequest: createOrder, uri: /mqtx/order, method=POST, body: {}", orderVo);
        orderService.createOrder(orderVo);
        return Result.ok();
    }
}

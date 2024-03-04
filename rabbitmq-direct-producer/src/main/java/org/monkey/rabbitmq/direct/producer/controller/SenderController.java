package org.monkey.rabbitmq.direct.producer.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.rabbitmq.direct.producer.pojo.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/rabbitmq/direct/")
@RestController
@Slf4j
public class SenderController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @PostMapping("/send/{msg}")
    public Result send(@PathVariable("msg") String msg) {
        log.info("收到消息：{}, 发送到mq.", msg);
        User user = new User();
        user.setName(msg);
        user.setAge(100);
        user.setPhone("1234567890");
        amqpTemplate.convertAndSend("direct_queue", user);
        return Result.ok();
    }
}

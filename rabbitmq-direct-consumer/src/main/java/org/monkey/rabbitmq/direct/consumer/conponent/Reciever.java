package org.monkey.rabbitmq.direct.consumer.conponent;

import lombok.extern.slf4j.Slf4j;
import org.monkey.rabbitmq.direct.consumer.pojo.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Reciever {

    @RabbitListener(queues = "direct_queue")
    public void recieve(User user) {
        log.info("收到消息：{}", user);
    }
}

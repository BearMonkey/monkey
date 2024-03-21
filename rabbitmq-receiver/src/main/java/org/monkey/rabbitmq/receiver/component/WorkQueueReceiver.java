package org.monkey.rabbitmq.receiver.component;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.rabbitmq.receiver.pojo.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WorkQueueReceiver {
    
    @RabbitListener(queues = "work_queue")
    public void recieve1(String msg) {
        try {
            log.info("1111 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @RabbitListener(queues = "work_queue")
    public void recieve2(String msg) {
        try {
            log.info("2222 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @RabbitListener(queues = "work_queue")
    public void recieve3(String msg) {
        try {
            log.info("3333 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

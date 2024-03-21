package org.monkey.rabbitmq.receiver.component;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.rabbitmq.receiver.pojo.User;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopicReceiver {
    //@RabbitListener(queues = "topic.queue1")
    public void receive1(String msg) {
        try {
            log.info("1111 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    //@RabbitListener(queues = "topic.queue2")
    public void receive2(String msg) {
        try {
            log.info("2222 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "topic.queue3",
                    durable = "true"
            ),
            exchange = @Exchange(
                    value = "topic_exchange",
                    ignoreDeclarationExceptions = "true",
                    type = "topic"
            ),
            key = "topic.odd.*"
    ))
    public void receive3(String msg) {
        try {
            log.info("3333 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = "topic.queue4",
                    durable = "true"
            ),
            exchange = @Exchange(
                    value = "topic_exchange",
                    ignoreDeclarationExceptions = "true",
                    type = "topic"
            ),
            key = "topic.even.*"
    ))
    public void receive4(String msg) {
        try {
            log.info("4444 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

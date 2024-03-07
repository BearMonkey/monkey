package org.monkey.rabbitmq.receiver.component;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.rabbitmq.receiver.pojo.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class DirectReceiver {
    @RabbitListener(queues = "direct.queue1")
    public void recieve1(String msg) {
        try {
            log.info("1111 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @RabbitListener(queues = "direct.queue2")
    public void recieve2(String msg) {
        try {
            log.info("2222 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @RabbitListener(queues = "direct.queue3")
    public void recieve3(String msg) {
        try {
            log.info("3333 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    /*@RabbitListener(
            bindings = @QueueBinding(
                value = @Queue(
                        value = "direct.queue.ack",
                        durable = "true",
                        arguments = {
                                // 绑定死信队列和死信交换机
                                @Argument(
                                        name = "x-dead-letter-exchange",
                                        value = "DeadExchange"
                                ),
                                @Argument(
                                        name = "x-dead-letter-routing-key",
                                        value = "DeadRoutingKey"
                                )
                        }
                ),
                exchange = @Exchange(
                        value = "direct_exchange",
                        ignoreDeclarationExceptions = "true",
                        type = "direct",
                        durable = "true"
                ),
                key = "direct.key.ack"
            )
    )*/
    @RabbitListener(queues = "direct.queue.ack")
    @RabbitHandler
    public void recieveAck(String msg, Channel channel, Message message) {
        log.info("收到消息：{}, body={}", message, msg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        MessageDeliveryMode deliveryMode = message.getMessageProperties().getDeliveryMode();
        MessageDeliveryMode receivedDeliveryMode = message.getMessageProperties().getReceivedDeliveryMode();
        log.info("deliveryTag: {}, deliveryMode: {}.", deliveryTag, receivedDeliveryMode);
        try {
            int a = 1/0;
            User user = JsonUtil.strToObj(msg, User.class);
            log.info(user.toString());
            //手动ack  第二个参数为false是表示仅仅确认当前消息 true表示确认之前所有的消息
            log.info("消息消费成功, 返回ack, deliveryTag: {}", deliveryTag);
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            //手动nack 告诉rabbitmq该消息消费失败  第三个参数：如果被拒绝的消息应该被重新请求，而不是被丢弃或变成死信，则为true
            try {
                log.info("消息消费失败, 返回nack, deliveryTag: {}", deliveryTag);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("消息消费失败, 返回nack失败, deliveryTag: {}", deliveryTag);
            }
        }
    }

    /*@RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            value = "dead_direct_queue",
                            durable = "true"
                    ),
                    exchange = @Exchange(
                            value = "dead_direct_exchange",
                            ignoreDeclarationExceptions = "true",
                            type = "direct",
                            durable = "true"
                    ),
                    key = "DeadRoutingKey"
            )
    )*/
    @RabbitListener(queues = "dead_direct_queue")
    @RabbitHandler
    public void deadReceiver(String msg, Channel channel, Message message) {
        log.info("死信队列收到消息：{}, body={}", message, msg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        MessageDeliveryMode deliveryMode = message.getMessageProperties().getDeliveryMode();
        MessageDeliveryMode receivedDeliveryMode = message.getMessageProperties().getReceivedDeliveryMode();
        log.info("deliveryTag: {}, deliveryMode: {}.", deliveryTag, receivedDeliveryMode);
        try {
            User user = JsonUtil.strToObj(msg, User.class);
            log.info("user: {}", user);
            log.info("死信队列消息消费成功, 返回ack, deliveryTag: {}", deliveryTag);
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            //手动nack 告诉rabbitmq该消息消费失败  第三个参数：如果被拒绝的消息应该被重新请求，而不是被丢弃或变成死信，则为true
            try {
                log.info("死信队列消息消费失败, 返回nack, deliveryTag: {}", deliveryTag);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("死信队列消息消费失败, 返回nack失败, deliveryTag: {}", deliveryTag);
            }
        }
    }
}

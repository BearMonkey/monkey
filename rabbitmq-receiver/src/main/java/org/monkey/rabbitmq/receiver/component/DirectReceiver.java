package org.monkey.rabbitmq.receiver.component;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.rabbitmq.common.config.DirectConfig;
import org.monkey.rabbitmq.common.config.FanoutConfig;
import org.monkey.rabbitmq.receiver.pojo.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class DirectReceiver {
    //@RabbitListener(queues = DirectConfig.DIRECT_QUEUE_1)

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = DirectConfig.DIRECT_QUEUE_1, durable = "true"),
            exchange = @Exchange(value = DirectConfig.DIRECT_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = {DirectConfig.DIRECT_ROUTING_KEY_1, DirectConfig.DIRECT_ROUTING_KEY_2}
    ))
    public void recieve1(String msg, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("1111 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("direct receiver1 comsume fail, return nack, deliveryTag={}, msg={}.", deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("direct receiver1 return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = DirectConfig.DIRECT_QUEUE_2, durable = "true"),
            exchange = @Exchange(value = DirectConfig.DIRECT_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = {DirectConfig.DIRECT_ROUTING_KEY_3}
    ))
    public void recieve2(String msg, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("2222 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("direct receiver2 comsume fail, return nack, deliveryTag={}, msg={}.", deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("direct receiver2 return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = DirectConfig.DIRECT_QUEUE_3, durable = "true"),
            exchange = @Exchange(value = DirectConfig.DIRECT_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = {DirectConfig.DIRECT_ROUTING_KEY_4}
    ))
    public void recieve3(String msg, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("3333 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("direct receiver3 comsume fail, return nack, deliveryTag={}, msg={}.", deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("direct receiver3 return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
    
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = DirectConfig.DIRECT_QUEUE_FAIL, durable = "true",
                    arguments = {
                            // 绑定死信队列和死信交换机
                            @Argument(name = "x-dead-letter-exchange", value = DirectConfig.DIRECT_DEAD_EXCHANGE),
                            @Argument(name = "x-dead-letter-routing-key", value = DirectConfig.DIRECT_DEAD_ROUTING_KEY)
                    }
            ),
            exchange = @Exchange(value = DirectConfig.DIRECT_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = DirectConfig.DIRECT_FAIL_ROUTING_KEY
    ))
    @RabbitHandler
    public void recieveFail(String msg, Channel channel, Message message) {
        log.info("recieveFail 收到消息：{}, body={}", message, msg);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        MessageDeliveryMode receivedDeliveryMode = message.getMessageProperties().getReceivedDeliveryMode();
        log.info("deliveryTag: {}, deliveryMode: {}.", deliveryTag, receivedDeliveryMode);
        try {
            // 消费必然失败，然后传给死信交换机
            int a = 1/0;
        } catch (Exception e) {
            try {
                log.info("recieveFail 消息消费失败, 返回nack, deliveryTag: {}", deliveryTag);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("recieveFail 返回nack失败, deliveryTag: {}", deliveryTag);
            }
        }
    }

    //@RabbitListener(queues = DirectConfig.DIRECT_DEAD_QUEUE)
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = DirectConfig.DIRECT_DEAD_QUEUE, durable = "true"),
            exchange = @Exchange(value = DirectConfig.DIRECT_DEAD_EXCHANGE, ignoreDeclarationExceptions = "true"),
            key = DirectConfig.DIRECT_DEAD_ROUTING_KEY
    ))
    @RabbitHandler
    public void deadReceiver(String msg, Channel channel, Message message) {
        log.info("死信队列收到消息: {}, body={}", message, msg);
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

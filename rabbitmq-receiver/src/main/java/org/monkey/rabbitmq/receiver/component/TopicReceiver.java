package org.monkey.rabbitmq.receiver.component;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.rabbitmq.common.config.TopicConfig;
import org.monkey.rabbitmq.receiver.pojo.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class TopicReceiver {
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = TopicConfig.TOPIC_QUEUE_1, durable = "true"),
            exchange = @Exchange(
                    value = TopicConfig.TOPIC_EXCHANGE,
                    ignoreDeclarationExceptions = "true",
                    type = "topic"),
            key = TopicConfig.TOPIC_ODD_ROUTING_KEY
    ))
    public void receive1(String msg, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("1111 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("topic receiver1 comsume fail, return nack, deliveryTag={}, msg={}.", deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("topic receiver1 return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
    
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = TopicConfig.TOPIC_QUEUE_2, durable = "true"),
            exchange = @Exchange(
                    value = TopicConfig.TOPIC_EXCHANGE,
                    ignoreDeclarationExceptions = "true",
                    type = "topic"),
            key = TopicConfig.TOPIC_EVEN_ROUTING_KEY
    ))
    public void receive2(String msg, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("2222 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("topic receiver2 comsume fail, return nack, deliveryTag={}, msg={}.", deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("topic receiver2 return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
}

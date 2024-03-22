package org.monkey.rabbitmq.receiver.component;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.rabbitmq.common.config.WorkerQueueConfig;
import org.monkey.rabbitmq.receiver.pojo.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class WorkQueueReceiver {

    @RabbitListener(queuesToDeclare = {@Queue(WorkerQueueConfig.WORK_QUEUE)})
    public void recieve1(String msg, Channel channel, Message message) {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("1111 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("workqueue receiver1 consume fail, will return nack, deliveryTag={}, msg={}.",
                        deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("workqueue receiver1 return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
    
    //@RabbitListener(queues = WorkerQueueConfig.WORK_QUEUE)
    @RabbitListener(queuesToDeclare = {@Queue(WorkerQueueConfig.WORK_QUEUE)})
    public void recieve2(String msg, Channel channel, Message message) {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("2222 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("workqueue receiver2 consume fail, will return nack, deliveryTag={}, msg={}.",
                        deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("workqueue receiver2 return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
    
    //@RabbitListener(queues = WorkerQueueConfig.WORK_QUEUE)
    @RabbitListener(queuesToDeclare = {@Queue(WorkerQueueConfig.WORK_QUEUE)})
    public void recieve3(String msg, Channel channel, Message message) {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("3333 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("workqueue receiver3 consume fail, will return nack, deliveryTag={}, msg={}.",
                        deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("workqueue receiver3 return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
}

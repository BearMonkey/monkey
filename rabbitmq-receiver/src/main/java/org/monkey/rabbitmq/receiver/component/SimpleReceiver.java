package org.monkey.rabbitmq.receiver.component;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.rabbitmq.common.config.SimpleConfig;
import org.monkey.rabbitmq.common.config.WorkerQueueConfig;
import org.monkey.rabbitmq.receiver.pojo.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SimpleReceiver {

    //@RabbitListener(queues = SimpleConfig.SIMPLE_QUEUE)
    //queuesToDeclare = {@Queue(WorkerQueueConfig.WORK_QUEUE)}
    @RabbitListener(queuesToDeclare = {@Queue(SimpleConfig.SIMPLE_QUEUE)})
    public void recieve1(String msg, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("simple receiver 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("simple receiver comsume fail, return nack, deliveryTag={}, msg={}.", deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("simple receiver return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }

    //@RabbitListener(queues = SimpleConfig.SIMPLE_QUEUE_WITH_ACK)
    @RabbitListener(queuesToDeclare = {@Queue(SimpleConfig.SIMPLE_QUEUE_WITH_ACK)})
    public void recieve2(String msg, Channel channel, Message message) {
        // log.info("1111 收到消息：{}", JsonUtil.strToObj(msg, User.class));
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("我一直在重试");
            int a = 1/0;
            User user = JsonUtil.strToObj(msg, User.class);
            log.info(user.toString());
            //手动ack  第二个参数为false是表示仅仅确认当前消息 true表示确认之前所有的消息
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            //手动nack 告诉rabbitmq该消息消费失败  第三个参数：如果被拒绝的消息应该被重新请求，而不是被丢弃或变成死信，则为true
            try {
                log.info(System.currentTimeMillis() + "");
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

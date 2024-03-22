package org.monkey.rabbitmq.receiver.component;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.rabbitmq.common.config.FanoutConfig;
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
public class FanoutReceiver {
    //@RabbitListener(queues = FanoutConfig.FANOUT_QUEUE_1)  // 这种方式不会自动创建
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = FanoutConfig.FANOUT_QUEUE_1,
                    durable = "true"
            ),
            exchange = @Exchange(
                    value = FanoutConfig.FANOUT_EXCHANGE,
                    ignoreDeclarationExceptions = "true",
                    type = "fanout"
            )
    ))
    public void recieve1(String msg, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("1111 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("recieve1 comsume, will return nack, deliveryTag={}, msg={}.", deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("recieve1 return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
    
    //@RabbitListener(queues = FanoutConfig.FANOUT_QUEUE_2)
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = FanoutConfig.FANOUT_QUEUE_2,
                    durable = "true"
            ),
            exchange = @Exchange(
                    value = FanoutConfig.FANOUT_EXCHANGE,
                    ignoreDeclarationExceptions = "true",
                    type = "fanout"
            )
    ))
    public void recieve2(String msg, Channel channel, Message message) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("2222 收到消息：{}", JsonUtil.strToObj(msg, User.class));
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            try {
                log.error("recieve2 comsume, will return nack, deliveryTag={}, msg={}.", deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("recieve2 return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
    
    
    /*@RabbitListener(bindings = @QueueBinding(
            value = @Queue(
                    value = FanoutConfig.FANOUT_QUEUE_3,
                    durable = "true"
            ),
            exchange = @Exchange(
                    value = FanoutConfig.FANOUT_EXCHANGE,
                    ignoreDeclarationExceptions = "true",
                    type = "fanout"
            )
    ))*/
    public void recieve3(String msg, Channel channel, Message message) {
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
                Thread.sleep(1000);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

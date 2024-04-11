package org.monkey.rabbitmq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitmqService implements RabbitTemplate.ReturnsCallback, RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange,String routingKey, Object msg) {
        // 设置交换机处理失败消息的模式     true 表示消息由交换机 到达不了队列时，会将消息重新返回给生产者
        // 如果不设置这个指令，则交换机向队列推送消息失败后，不会触发 setReturnCallback
        rabbitTemplate.setMandatory(true);
        //消息消费者确认收到消息后，手动ack回执
        rabbitTemplate.setConfirmCallback(this);

        // 暂时关闭 return 配置
        //rabbitTemplate.setReturnCallback(this);
        //发送消息
        rabbitTemplate.convertAndSend(exchange, routingKey, msg);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("---- confirm ----ack={},  cause={}", ack, cause);
        log.info("correlationData -->{}", correlationData);
        if(ack){
            // 交换机接收到
            log.info("---- confirm ----ack==true  cause={}", cause);
        }else{
            // 没有接收到
            log.info("---- confirm ----ack==false  cause={}", cause);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        String replyText = returned.getReplyText();
        int replyCode = returned.getReplyCode();
        log.info("---- returnedMessage ----replyCode={}, replyText={}", replyCode, replyText);
    }
}

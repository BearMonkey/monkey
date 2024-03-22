package org.monkey.rabbitmq.sender.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.common.utils.JsonUtil;
import org.monkey.rabbitmq.common.config.*;
import org.monkey.rabbitmq.sender.pojo.User;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("/rabbitmq/sender")
@RestController
@Slf4j
public class SenderController {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/simple/{msg}")
    public Result send(@PathVariable("msg") String msg) {
        log.info("收到消息：{}, 发送到mq.", msg);
        User user = new User();
        user.setName(msg);
        user.setAge(100);
        user.setPhone("1234567890");
        amqpTemplate.convertAndSend(SimpleConfig.SIMPLE_QUEUE, JsonUtil.objToStr(user));
        return Result.ok();
    }
    
    /**
     * 工作队列模式下发型消息
     *
     * @return Result
     */
    @PostMapping("/workqueue")
    public Result workerQueueSend() {
        log.info("send msg by rabbitmq use workqueue.");
        for (int i = 10000; i < 10010; i++) {
            User user = new User();
            user.setName(i + "");
            user.setAge(i);
            user.setPhone(i + "");
            amqpTemplate.convertAndSend(WorkerQueueConfig.WORK_QUEUE, JsonUtil.objToStr(user));
        }
        return Result.ok();
    }
    
    /**
     * Fanout模式下发消息
     * 发送消息时routingkey使用空字符串
     *
     * @return Result
     */
    @PostMapping("/fanout")
    public Result fanoutSend() {
        String exchange = FanoutConfig.FANOUT_EXCHANGE;
        for (int i = 20; i < 25; i++) {
            User user = new User();
            user.setName("100" + i);
            user.setAge(i);
            user.setPhone(i + "");
            amqpTemplate.convertAndSend(exchange, "", JsonUtil.objToStr(user));
        }
        return Result.ok();
    }
    
    /**
     * Direct模式下发消息
     * 生产者把消息发送给交换机，
     * 交换机把消息发送给与routingKey完全的队列，重点：完全匹配
     * 消费者1 接收 direct.key.1111、direct.key.2222
     * 消费者2 接收 direct.key.3333
     * 消费者3 接收 direct.key.4444
     *
     * @return Result
     */
    @PostMapping("/direct")
    public Result directSend() {
        String exchange = DirectConfig.DIRECT_EXCHANGE;
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("1000" + i);
            user.setAge(i);
            user.setPhone(i + "");
            if (i < 3) {
                amqpTemplate.convertAndSend(exchange, DirectConfig.DIRECT_ROUTING_KEY_1, JsonUtil.objToStr(user));
            } else if (i < 6) {
                amqpTemplate.convertAndSend(exchange, DirectConfig.DIRECT_ROUTING_KEY_2, JsonUtil.objToStr(user));
            }  else if (i < 9) {
                amqpTemplate.convertAndSend(exchange, DirectConfig.DIRECT_ROUTING_KEY_3, JsonUtil.objToStr(user));
            } else {
                amqpTemplate.convertAndSend(exchange, DirectConfig.DIRECT_ROUTING_KEY_4, JsonUtil.objToStr(user));
            }
        }
        return Result.ok();
    }

    /**
     * 消息消费失败测试，失败后进入死信队列
     *
     * @return Result
     */
    @PostMapping("/direct/fail")
    public Result directFail() {
        log.info("Test direct send, and return nack, /rabbitmq/sender/direct/fail");

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setDeliveryTag(1111L);
        messageProperties.setReceivedDeliveryMode(MessageDeliveryMode.PERSISTENT);

        User user = new User();
        user.setName("monkey");
        user.setAge(20);
        user.setPhone("123456");

        Message message = rabbitTemplate.getMessageConverter()
                .toMessage(Objects.requireNonNull(JsonUtil.objToStr(user)), messageProperties);
        rabbitTemplate.convertAndSend(DirectConfig.DIRECT_EXCHANGE, DirectConfig.DIRECT_FAIL_ROUTING_KEY, message);
        log.info("send msg to mq, message={}", message);
        return Result.ok();
    }

    /**
     * 消息消费失败测试，失败后进入死信队列
     *
     * @param keys
     * @return
     */
    @PostMapping("/direct/ack")
    public Result directSendWithAck(@RequestBody String[] keys) {
        log.info("receive request: /rabbitmq/sender/direct/ack, keys={}", keys);
        if (keys == null) {
            return Result.error("未指定key，消息投递失败.");
        }

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setDeliveryTag(1111L);
        messageProperties.setReceivedDeliveryMode(MessageDeliveryMode.PERSISTENT);

        for (String key : keys) {
            User user = new User();
            user.setName(key);
            user.setAge(20);
            user.setPhone("key: " + key);

            Message message = rabbitTemplate.getMessageConverter()
                    .toMessage(Objects.requireNonNull(JsonUtil.objToStr(user)), messageProperties);

            log.info("投递消息：{}", message);
            rabbitTemplate.convertAndSend(DirectConfig.DIRECT_EXCHANGE, key, message);
        }
        return Result.ok();
    }
    
    /**
     * 主题模式：topic发送消息
     *
     * routingkey由多个单词组成，每个单词 由 . 隔开
     * # 表示匹配一个或多个单词
     * * 表示只匹配一个单词
     *
     * 每条消息的routingkey都不同，根据消息的编号
     * topic.odd.${num}   奇数消息名称格式
     * topic.even.${num}  偶数消息名称格式
     *
     *
     * @return Result
     */
    @PostMapping("/topic")
    public Result topicSend() {
        String exchange = TopicConfig.TOPIC_EXCHANGE;
        for (int i = 10; i < 20; i++) {
            User user = new User();
            user.setName("100" + i);
            user.setAge(i);
            user.setPhone(i + "");
            if (i % 2 == 0) {
                amqpTemplate.convertAndSend(exchange, TopicConfig.TOPIC_EVEN_ROUTING_KEY + i, JsonUtil.objToStr(user));
            } else {
                amqpTemplate.convertAndSend(exchange, TopicConfig.TOPIC_ODD_ROUTING_KEY + i, JsonUtil.objToStr(user));
            }
        }
        return Result.ok();
    }
}



class MyConfirmCallback implements RabbitTemplate.ConfirmCallback {

    /**
     * @param correlationData   相关配置信息
     * @param ack       exchange交换机是否成功收到信息
     * @param cause     失败原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("confirm running ...");
        if (ack){
            System.out.println("成功接收");
        }else {
            System.out.println("接收失败:" + cause);
        }
    }
}

@Slf4j
class MyReturnCallback implements RabbitTemplate.ReturnsCallback {
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        Message message = returned.getMessage();
        String exchange = returned.getExchange();
        String routingKey = returned.getRoutingKey();
        int replyCode = returned.getReplyCode();
        String replyText = returned.getReplyText();
        log.info("message: {}", message);
        log.info("exchange: {}", exchange);
        log.info("routingKey: {}", routingKey);
        log.info("replyCode: {}", replyCode);
        log.info("replyText: {}", replyText);
    }
}
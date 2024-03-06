package org.monkey.rabbitmq.sender.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.common.utils.JsonUtil;
import org.monkey.rabbitmq.sender.pojo.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

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
        amqpTemplate.convertAndSend("simple_queue", JsonUtil.objToStr(user));
        return Result.ok();
    }
    
    /**
     * 工作队列模式下发型消息
     *
     * @return Result
     */
    @PostMapping("/workqueue")
    public Result workerQueueSend() {
        for (int i = 20; i < 40; i++) {
            User user = new User();
            user.setName("100" + i);
            user.setAge(i);
            user.setPhone(i + "");
            amqpTemplate.convertAndSend("work_queue", JsonUtil.objToStr(user));
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
        String exchange = "fanout_exchange";
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
        String exchange = "direct_exchange";
        for (int i = 10; i < 20; i++) {
            User user = new User();
            user.setName("100" + i);
            user.setAge(i);
            user.setPhone(i + "");
            int n = i - 10;
            if (n < 3) {
                amqpTemplate.convertAndSend(exchange, "direct.key.1111", JsonUtil.objToStr(user));
            } else if (n < 6) {
                amqpTemplate.convertAndSend(exchange, "direct.key.2222", JsonUtil.objToStr(user));
            }  else if (n < 10) {
                amqpTemplate.convertAndSend(exchange, "direct.key.3333", JsonUtil.objToStr(user));
            } else {
                amqpTemplate.convertAndSend(exchange, "direct.key.4444", JsonUtil.objToStr(user));
            }
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
        String exchange = "topic_exchange";
        for (int i = 10; i < 20; i++) {
            User user = new User();
            user.setName("100" + i);
            user.setAge(i);
            user.setPhone(i + "");
            int n = i - 10;
            if (i % 2 == 0) {
                amqpTemplate.convertAndSend(exchange, "topic.even." + i, JsonUtil.objToStr(user));
            } else {
                amqpTemplate.convertAndSend(exchange, "topic.odd." + i, JsonUtil.objToStr(user));
            }
        }
        return Result.ok();
    }

    @PostMapping("/direct/confirm")
    public Result directWithConfirm() {

        // 定义回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

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
        });
        User user = new User();
        user.setName("10010");
        user.setAge(10);
        user.setPhone(10 + "");
        String exchange = "direct_exchange";
        amqpTemplate.convertAndSend(exchange, "direct.key.confirm", JsonUtil.objToStr(user));
        return Result.ok();
    }


    public boolean submitTask() {
        CorrelationData correlationData = new CorrelationData();
        try {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType("application/json");
            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            messageProperties.setDeliveryTag(1111L);

            // 通过Converter将对象转为Message
            Message message = rabbitTemplate.getMessageConverter().toMessage("xxxx", messageProperties);

            rabbitTemplate.convertAndSend("flashOrderTask.exchange", "order.task", message, correlationData);
            CorrelationData.Confirm confirm = correlationData.getFuture().get(5, TimeUnit.SECONDS);
            if (!confirm.isAck()) {
                log.info("消息发送失败 {}",confirm.getReason());
                throw new RuntimeException("消息发送失败");
            } else {
                log.info("消息发送成功");
            }
        } catch (Exception e) {
            log.info("下单任务提交失败 {} {}", 1111L, e.getMessage());
            return false;
        }
        return true;
    }

}

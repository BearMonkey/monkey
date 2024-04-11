package org.monkey.mqtx.agent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.common.utils.JsonUtil;
import org.monkey.mqtx.agent.pojo.Account;
import org.monkey.mqtx.agent.service.AgentService;
import org.monkey.rabbitmq.common.config.AccountMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/mqtx/agent")
public class AgentController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AgentService agentService;

    //@PutMapping("/tasksConfig")
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = AccountMqConfig.QUEUE_ACCOUNT_MANAGER, declare = "true"),
            exchange = @Exchange(value = AccountMqConfig.EXCHANGE_DIRECT_ACCOUNT_MANAGER, durable = "true"),
            key = AccountMqConfig.KEY_ACCOUNT_MANAGER
    ))
    // public String modifyAccountConfig(@RequestBody String accountStr) {
    public void modifyAccountConfig(String msg, Channel channel, Message message) {
        log.info("收到 account 消息：{}, message={}", msg, message);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            if (null == msg) {
                log.error("消息为空");
                channel.basicNack(deliveryTag,true,false);
                return;
            }

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(msg);
            JsonNode nameNode = jsonNode.get("accountNo");
            JsonNode passNode = jsonNode.get("accountPwd");
            JsonNode typeNode = jsonNode.get("accountType");
            Account account = new Account();
            account.setName(nameNode.textValue());
            account.setPass(passNode.textValue());
            account.setType(typeNode.textValue());
            agentService.modifyConfig(account);
            log.info("account 消息处理完成");
            //channel.basicAck(deliveryTag,true);
            channel.basicNack(deliveryTag,true,false);

        } catch (IOException e) {
            try {
                log.error("comsume fail, return nack, deliveryTag={}, msg={}.", deliveryTag, msg);
                channel.basicNack(deliveryTag,true,false);
            } catch (IOException ex) {
                log.error("return nack fail, deliveryTag={}, msg={}.", deliveryTag, msg);
            }
        }
    }
}

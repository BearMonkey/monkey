package org.monkey.mqtx.account.service.impl.account;

import org.monkey.mqtx.account.enums.MsgStatusEnum;
import org.monkey.mqtx.account.service.AccountMsgService;
import org.monkey.rabbitmq.common.config.AccountMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountMsgService accountMsgService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("消息传递回, ack={}, correlationData={}, cause={}", ack, correlationData, cause);
        if (ack) {
            log.info("消息传递成功");
            if (correlationData == null) {
                log.warn("confirmcallback未获取到消息");
                return;
            }
            // 修改状态为已消费
            accountMsgService.modifyMsgStatus(correlationData.getId(), MsgStatusEnum.COMSUMED.getName());
            return;
        }

        log.info("消息传递失败");
        ReturnedMessage returned = correlationData.getReturned();
        if (returned == null) {
            log.warn("失败回调内容没有 ReturnedMessage");
            return;
        }

        // 需要重新发送
        Message message = Objects.requireNonNull(correlationData.getReturned()).getMessage();
        log.info("重新发送消息：message={}", message);
        // 打印日志，说明发送失败，将会由定时线程执行重发
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
        rabbitTemplate.convertAndSend(AccountMqConfig.EXCHANGE_DIRECT_ACCOUNT_MANAGER,
                AccountMqConfig.KEY_ACCOUNT_MANAGER,
                message,
                correlationData);
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.info("AccountReturnsCallback # returnedMessage");
        log.info("returned: {}", returned);
    }
}

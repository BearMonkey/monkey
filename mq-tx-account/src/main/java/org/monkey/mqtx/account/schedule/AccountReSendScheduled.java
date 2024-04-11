package org.monkey.mqtx.account.schedule;

import org.monkey.mqtx.account.pojo.AccountMsg;
import org.monkey.mqtx.account.service.AccountMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AccountReSendScheduled {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AccountMsgService accountMsgService;

    @Scheduled(initialDelay = 1000,fixedDelay = 5000)
    @Transactional
    public void scan(){
        List<AccountMsg> allFails = accountMsgService.findAllFail();
        if (allFails.isEmpty()) {
            return;
        }
        log.info("定时任务执行：查询所有发送失败的消息");
        for (AccountMsg accountMsg : allFails) {
            String messageId = accountMsg.getMessageId();
        }
        /*List<AccountInfoLog> accountInfoLogs = accountInfoDao.selectFailAcount();
        if (!CollectionUtils.isEmpty(accountInfoLogs)){

            accountInfoLogs.forEach(accountInfoLog -> {
                System.out.println("定时任务执行");
                accountInfoDao.updAccountLog(accountInfoLog.getTxNo(),"Y");

                rabbitTemplate.setConfirmCallback(confirmService);
                rabbitTemplate.setReturnCallback(confirmService);
                rabbitTemplate.convertAndSend("account_exchange", "account", JSONObject.toJSONString(accountInfoLog), message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                },new CorrelationData(accountInfoLog.getTxNo()));
            });
        }*/

    }
}

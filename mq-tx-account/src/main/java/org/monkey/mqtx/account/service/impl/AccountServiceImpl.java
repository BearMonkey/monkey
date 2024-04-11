package org.monkey.mqtx.account.service.impl;

import org.monkey.common.utils.JsonUtil;
import org.monkey.mqtx.account.enums.MsgStatusEnum;
import org.monkey.mqtx.account.exception.AccountException;
import org.monkey.mqtx.account.mapper.AccountMapper;
import org.monkey.mqtx.account.pojo.Account;
import org.monkey.mqtx.account.service.AccountMsgService;
import org.monkey.mqtx.account.service.AccountService;
import org.monkey.mqtx.account.service.impl.account.AccountCallback;
import org.monkey.rabbitmq.common.config.AccountMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountMsgService accountMsgService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AccountCallback accountCallback;

    @Override
    public void addAccount(Account account) throws AccountException {
        log.info("开始执行添加账号, accountNo={}", account.getAccountNo());
        Account accounts = accountMapper.selctByAccountNo(account.getAccountNo());
        if (accounts != null) {
            throw new AccountException("账号已存在.");
        }
        accountMapper.insert(account);
        log.info("执行添加账号成功, accountNo={}", account.getAccountNo());
    }

    @Override
    @Transactional
    public void updateAccount(Account account) throws AccountException {
        log.info("开始执行修改账号, accountNo={}", account.getAccountNo());
        Account accountInDb = accountMapper.selctByAccountNo(account.getAccountNo());
        if (null == accountInDb) {
            throw new AccountException("账号不存在.");
        }
        account.setUpdateTime(new Date());

        // 生成消息id，用于传递和写入本地消息表
        String uuid = UUID.randomUUID().toString();
        log.info("修改账号信息表");
        accountMapper.update(account);
        // 本地消息表写入
        accountMsgService.writeAccountMsg(uuid, MsgStatusEnum.NOT_CONSUME.getName());

        log.info("完成账号数据库的修改, 向agent发送消息");
        sendMsgToMq(account, uuid);
        log.info("向agent发送消息成功");

        log.info("执行修改账号成功, accountNo={}", account.getAccountNo());
    }

    @Override
    public List<Account> queryAllAccount() {
        return accountMapper.selctAll();
    }

    @Override
    public Account queryByAccountNo(String accountNo) {
        return accountMapper.selctByAccountNo(accountNo);
    }

    @Override
    public Account queryById(Integer id) {
        return accountMapper.selctById(id);
    }

    private void sendMsgToMq(Account account, String messageId) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setReceivedDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.setMessageId(messageId);
        Message message = rabbitTemplate.getMessageConverter()
                .toMessage(Objects.requireNonNull(JsonUtil.objToStr(account)), messageProperties);

        log.info("messageId={}", messageId);
        CorrelationData correlationData = new CorrelationData(messageId);
        correlationData.setReturned(new ReturnedMessage(message, 0, "",
                AccountMqConfig.EXCHANGE_DIRECT_ACCOUNT_MANAGER,
                AccountMqConfig.KEY_ACCOUNT_MANAGER));
        rabbitTemplate.convertAndSend(AccountMqConfig.EXCHANGE_DIRECT_ACCOUNT_MANAGER,
                AccountMqConfig.KEY_ACCOUNT_MANAGER,
                message,
                correlationData);

        // 设置ComfirmCallback 和 ReturnCallback
        rabbitTemplate.setReturnsCallback(accountCallback);
        rabbitTemplate.setConfirmCallback(accountCallback);
    }
}

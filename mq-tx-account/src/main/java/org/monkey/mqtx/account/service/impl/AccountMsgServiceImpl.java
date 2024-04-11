package org.monkey.mqtx.account.service.impl;

import org.monkey.mqtx.account.mapper.AccountMsgMapper;
import org.monkey.mqtx.account.pojo.AccountMsg;
import org.monkey.mqtx.account.service.AccountMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountMsgServiceImpl implements AccountMsgService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountMsgMapper accountMsgMapper;

    /**
     * 写入本地消息表
     *
     * @param messageId messageId
     * @param messageStatus messageStatus
     */
    @Override
    public void writeAccountMsg(String messageId, String messageStatus) {
        log.info("写入本地消息表 start");
        AccountMsg accountMsg = new AccountMsg();
        accountMsg.setMessageStatus(messageStatus);
        accountMsg.setMessageId(messageId);
        accountMsg.setCreateTime(new Date());
        accountMsg.setCreateUser("system auto");
        accountMsgMapper.insert(accountMsg);
        log.info("写入本地消息表 end");
    }

    @Override
    public AccountMsg find(String messageId) {
        log.info("根据消息id查询本地消息表, messageId={}", messageId);
        return accountMsgMapper.selctByAccountMsg(messageId);
    }

    /**
     * 修改本地消息表中的消息状态
     *
     * @param messageId 消息id
     * @param messageStatus 消息状态
     */
    @Override
    public void modifyMsgStatus(String messageId, String messageStatus) {
        log.info("根据消息id修改本地消息表中消息的消费状态, messageId={}, messageStatus={}", messageId, messageStatus);
        AccountMsg accountMsg = new AccountMsg();
        accountMsg.setMessageStatus(messageStatus);
        accountMsg.setMessageId(messageId);
        accountMsg.setUpdateTime(new Date());
        accountMsg.setUpdateUser("system auto");
        accountMsgMapper.update(accountMsg);
    }

    @Override
    public List<AccountMsg> findAllFail() {
        return null;
    }

}

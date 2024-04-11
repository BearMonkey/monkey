package org.monkey.mqtx.account.service;

import org.monkey.mqtx.account.pojo.AccountMsg;

import java.util.List;

public interface AccountMsgService {

    void writeAccountMsg(String messageId, String messageStatus);

    AccountMsg find(String messageId);

    void modifyMsgStatus(String messageId, String messageStatus);

    List<AccountMsg> findAllFail();
}

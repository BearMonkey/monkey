package org.monkey.mqtx.account.service;

import org.monkey.mqtx.account.AccountException;
import org.monkey.mqtx.account.pojo.Account;

import java.util.List;

public interface AccountService {
    void addAccount(Account account) throws AccountException;

    void updateAccount(Account account) throws AccountException;

    List<Account> queryAllAccount();

    List<Account> queryByAccountNo(String accountNo);

    Account queryById(Integer id);

}

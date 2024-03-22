package org.monkey.mqtx.account.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.mqtx.account.AccountException;
import org.monkey.mqtx.account.mapper.AccountMapper;
import org.monkey.mqtx.account.pojo.Account;
import org.monkey.mqtx.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void addAccount(Account account) throws AccountException {
        log.info("开始执行添加账号, accountNo={}", account.getAccountNo());
        List<Account> accounts = accountMapper.selctByAccountNo(account.getAccountNo());
        if (accounts != null && !accounts.isEmpty()) {
            throw new AccountException("账号已存在.");
        }
        accountMapper.insert(account);
        log.info("执行添加账号成功, accountNo={}", account.getAccountNo());
    }

    @Override
    public void updateAccount(Account account) throws AccountException {
        log.info("开始执行修改账号, accountNo={}", account.getAccountNo());
        List<Account> accounts = accountMapper.selctByAccountNo(account.getAccountNo());
        if (null == accounts || accounts.isEmpty()) {
            throw new AccountException("账号不存在.");
        }
        account.setUpdateTime(new Date());
        accountMapper.update(account);
        log.info("执行修改账号成功, accountNo={}", account.getAccountNo());
    }

    @Override
    public List<Account> queryAllAccount() {
        return accountMapper.selctAll();
    }

    @Override
    public List<Account> queryByAccountNo(String accountNo) {
        return accountMapper.selctByAccountNo(accountNo);
    }

    @Override
    public Account queryById(Integer id) {
        return accountMapper.selctById(id);
    }
}

package org.monkey.mqtx.agent.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.monkey.mqtx.agent.dao.TaskDao;
import org.monkey.mqtx.agent.exception.AgentException;
import org.monkey.mqtx.agent.pojo.Account;
import org.monkey.mqtx.agent.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AgentServiceImpl implements AgentService {
    @Autowired
    private TaskDao taskDao;


    @Override
    public void modifyConfig(Account account) {
        try {
            taskDao.saveTaskConfig(account);
        } catch (AgentException e) {
            log.error("修改账号配置失败, name={}", account.getName());
        }
    }

    @Override
    public void addConfig(Account account) {
        try {
            taskDao.saveTaskConfig(account);
        } catch (AgentException e) {
            log.error("增加账号配置失败, name={}", account.getName());
        }
    }
}

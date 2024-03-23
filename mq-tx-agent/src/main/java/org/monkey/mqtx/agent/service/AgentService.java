package org.monkey.mqtx.agent.service;

import org.monkey.mqtx.agent.pojo.Account;

public interface AgentService {
    void modifyConfig(Account account);
    void addConfig(Account account);
}

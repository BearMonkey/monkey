package org.monkey.mqtx.agent.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.common.utils.JsonUtil;
import org.monkey.mqtx.agent.pojo.Account;
import org.monkey.mqtx.agent.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mqtx/agent")
@Slf4j
public class AgentController {

    @Autowired
    private AgentService agentService;

    @PutMapping("/tasksConfig")
    public Result modifyAccountConfig(@RequestBody String accountStr) {
        log.info("收到请求: /mqtx/agent/tasksConfig, body={}", accountStr);
        if (null == accountStr) {
            return Result.error("请求body为空");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(accountStr);
            JsonNode nameNode = jsonNode.get("accountNo");
            JsonNode passNode = jsonNode.get("accountPwd");
            JsonNode typeNode = jsonNode.get("accountType");
            Account account = new Account();
            account.setName(nameNode.textValue());
            account.setPass(passNode.textValue());
            account.setType(typeNode.textValue());
            agentService.modifyConfig(account);
            log.info("处理完成: /mqtx/agent/tasksConfig, body={}", accountStr);
            return Result.ok();
        } catch (JsonProcessingException e) {
            return Result.error("body 转换失败, 数据格式不正确");
        }
    }
}

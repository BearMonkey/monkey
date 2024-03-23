package org.monkey.mqtx.agent.dao;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.FileUtil;
import org.monkey.mqtx.agent.config.AgentConfig;
import org.monkey.mqtx.agent.exception.AgentException;
import org.monkey.mqtx.agent.pojo.Account;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class TaskDao {
    public void saveTaskConfig(Account account) throws AgentException {
        if (null == account) {
            throw new AgentException("需要写入的内容为空");
        }
        log.info("根据经：{}", new File("/").getAbsolutePath());
        String fileName = account.getName() + ".json";
        String filePath = AgentConfig.TASK_DIR + "/" + fileName;
        if (!FileUtil.exist(filePath)) {
            if (!FileUtil.createFile(filePath)) {
                log.error("文件不存在，并且创建文件失败: {}", filePath);
                return;
            }
            log.info("文件不存在，创建文件: {}", filePath);
        }
        try {
            log.info("绝对路径: {}", new File(filePath).getAbsoluteFile());
            FileUtil.write(filePath, account);
            log.info("账号配置写入文件完成, name={}", account.getName());
        } catch (IOException e) {
            throw new AgentException(String.format("读取文件内容失败：%s", filePath), e);
        }

    }
}

package org.monkey.user.xxljob.handler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.monkey.oauth.common.utils.JsonUtil;
import org.monkey.user.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

import static org.monkey.oauth.common.utils.JsonUtil.*;

@Slf4j
@Component
public class BatchInsertHandler {
    @Autowired
    private BookService bookService;

    @XxlJob("BatchInsertXxlJobHandler")
    public ReturnT<String> addBook() {
        String param = XxlJobHelper.getJobParam();
        log.info("小熊学Java 任务方法触发成功, param={}", param);
        Map map = JsonUtil.strToObj(param, Map.class);
        if (null == map || map.isEmpty()) {
            return ReturnT.FAIL;
        }
        try {
            bookService.addBook((String) map.get("name"));
        } catch (Exception e) {
            return ReturnT.FAIL;
        }
        return ReturnT.SUCCESS;
    }
}

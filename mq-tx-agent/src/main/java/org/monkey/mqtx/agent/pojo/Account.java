package org.monkey.mqtx.agent.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

@Data
public class Account {
    private String name;
    private String pass;
    private String type;

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}

package org.monkey.rabbitmq.receiver.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

@Data
public class User {
    private String name;
    private String phone;
    
    private Integer age;
    
    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}

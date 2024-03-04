package org.monkey.rabbitmq.direct.consumer.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String name;
    private String phone;

    private Integer age;

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}

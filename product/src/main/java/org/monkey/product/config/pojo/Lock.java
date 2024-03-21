package org.monkey.product.config.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

@Data
public class Lock {
    /**
     * key名
     */
    private String name;
    
    /**
     * value值
     */
    private String value;
    
    public Lock(String name, String value) {
        this.name = name;
        this.value = value;
    }
    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}

package org.monkey.mqtx.product.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

import java.util.Date;

@Data
public class ProductLock {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private boolean status;

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}

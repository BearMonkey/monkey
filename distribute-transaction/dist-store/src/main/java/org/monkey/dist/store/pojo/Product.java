package org.monkey.dist.store.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {
    private Integer id;
    private String productName;
    private Integer productInventory;
    private BigDecimal price;
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

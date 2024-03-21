package org.monkey.dist.order.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private Integer id;
    private String orderNo;
    private BigDecimal totlePrice;
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

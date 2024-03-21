package org.monkey.mqtx.order.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDetail {
    private Integer id;
    private String orderNo;
    private Integer productId;
    private String productName;
    private int number;
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

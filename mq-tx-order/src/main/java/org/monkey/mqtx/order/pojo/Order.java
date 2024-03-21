package org.monkey.mqtx.order.pojo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order {
    private Integer id;
    private String orderNo; // 订单编号
    private Integer productId; // 商品id
    private BigDecimal totlePrice; // 总价
    private String orderState; // 订单状态 new(新订单,待支付), pay(已支付,待扣库存), fin(已扣库存,订单完成), exp(新订单,未支付已过期)
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
    private boolean status; // 记录状态

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}

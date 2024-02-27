package org.monkey.dist.order.dto;

import lombok.Data;
import org.monkey.oauth.common.utils.JsonUtil;

import java.math.BigDecimal;

@Data
public class ProductInfo {

    private Integer productId;
    private String productName;
    private int number;
    private BigDecimal price;

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}

package org.monkey.mqtx.common.vo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

import java.math.BigDecimal;

@Data
public class ProductVo {

    private Integer productId;
    private String productName;
    private int number;
    private BigDecimal price;

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}

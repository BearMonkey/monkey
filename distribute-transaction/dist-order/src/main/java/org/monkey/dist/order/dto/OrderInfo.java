package org.monkey.dist.order.dto;

import lombok.Data;
import org.monkey.oauth.common.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderInfo {
    private Integer id;
    private String orderNo;
    private List<ProductInfo> products = new ArrayList<>();
    private String createUser;

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}

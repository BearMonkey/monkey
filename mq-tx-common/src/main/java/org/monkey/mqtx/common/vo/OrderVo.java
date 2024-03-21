package org.monkey.mqtx.common.vo;

import lombok.Data;
import org.monkey.common.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderVo {
    private Integer id;
    private String orderNo;
    private ProductVo product;
    private Integer createUserId;
    private String createUser;
    private String orderState;

    @Override
    public String toString() {
        return JsonUtil.objToStr(this);
    }
}

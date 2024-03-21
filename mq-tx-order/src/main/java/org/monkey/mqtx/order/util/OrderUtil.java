package org.monkey.mqtx.order.util;

import org.apache.commons.collections4.CollectionUtils;
import org.monkey.mqtx.common.vo.OrderVo;
import org.monkey.mqtx.common.vo.ProductVo;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author Monkey
 */
public class OrderUtil {

    public static String generateOrderNo(String user) {
        return user + UUID.randomUUID();
    }

    public static BigDecimal calcTotlePrice(OrderVo orderVo) {
        return orderVo.getProduct().getPrice().multiply(new BigDecimal(orderVo.getProduct().getNumber()));
    }
}

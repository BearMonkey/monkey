package org.monkey.dist.order.util;

import org.apache.commons.collections4.CollectionUtils;
import org.monkey.dist.order.dto.OrderInfo;
import org.monkey.dist.order.dto.ProductInfo;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.UUID;

/**
 * @author Monkey
 */
public class OrderUtil {

    public static boolean emptyOrder(OrderInfo orderInfo) {
        if (orderInfo == null) {
            return true;
        }
        return CollectionUtils.isEmpty(orderInfo.getProducts());
    }

    public static void generateOrderNo(OrderInfo orderInfo) {
        orderInfo.setOrderNo(UUID.randomUUID().toString());
    }

    public static BigDecimal calcTotlePrice(OrderInfo orderInfo) {
        BigDecimal totlePrice = new BigDecimal(0);
        for (ProductInfo product : orderInfo.getProducts()) {
            BigDecimal multiply = product.getPrice().multiply(new BigDecimal(product.getNumber()));
            totlePrice = totlePrice.add(multiply);
        }
        return totlePrice;
    }
}

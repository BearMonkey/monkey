package org.monkey.dist.order.service;

import org.monkey.dist.order.dto.OrderInfo;

import java.util.List;

public interface OrderService {
    void createOrder(OrderInfo orderInfo);

    void delOrder(String orderNo);

    void updateOrder(OrderInfo orderInfo);

    List<OrderInfo> listOrder();

    OrderInfo showOrder(String orderNo);
}

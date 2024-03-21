package org.monkey.mqtx.order.service;

import org.monkey.mqtx.common.vo.OrderVo;

public interface OrderService {
    /**
     * 创建订单
     *
     * @param orderVo 订单视图对象
     */
    void createOrder(OrderVo orderVo);
}

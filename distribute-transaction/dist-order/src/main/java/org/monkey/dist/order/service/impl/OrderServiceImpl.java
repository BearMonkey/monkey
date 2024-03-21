package org.monkey.dist.order.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.monkey.dist.order.dto.OrderInfo;
import org.monkey.dist.order.dto.ProductInfo;
import org.monkey.dist.order.mapper.OrderDetailMapper;
import org.monkey.dist.order.mapper.OrderMapper;
import org.monkey.dist.order.pojo.Order;
import org.monkey.dist.order.pojo.OrderDetail;
import org.monkey.dist.order.service.OrderService;
import org.monkey.dist.order.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    private final Lock lock = new ReentrantLock();


    @Override
    @Transactional
    public void createOrder(OrderInfo orderInfo) {
        if (OrderUtil.emptyOrder(orderInfo)) {
            log.info("订单信息为空");
            return;
        }
        OrderUtil.generateOrderNo(orderInfo);

        Order order = new Order();
        order.setOrderNo(orderInfo.getOrderNo());
        order.setTotlePrice(OrderUtil.calcTotlePrice(orderInfo));
        order.setCreateUser(orderInfo.getCreateUser());


        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ProductInfo product : orderInfo.getProducts()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderNo(orderInfo.getOrderNo());
            orderDetail.setProductId(product.getProductId());
            orderDetail.setProductName(product.getProductName());
            orderDetail.setNumber(product.getNumber());
            orderDetail.setPrice(product.getPrice());
            orderDetail.setCreateUser(orderInfo.getCreateUser());
            orderDetails.add(orderDetail);
        }

        try {
            lock.lock();
            log.info("start write order to db");
            orderMapper.add(order);
            orderDetailMapper.batchInsert(orderDetails);
            // 扣减库存

            log.info("start write order to db success");
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public void delOrder(String orderNo) {

    }

    @Override
    @Transactional
    public void updateOrder(OrderInfo orderInfo) {

    }

    @Override
    public List<OrderInfo> listOrder() {
        List<OrderInfo> ret = new ArrayList<>();
        List<Order> orders = orderMapper.queryAll();
        for (Order order : orders) {
            ret.add(getOrderInfo(order));
        }
        return ret;
    }

    @Override
    public OrderInfo showOrder(String orderNo) {
        Order order = orderMapper.queryByOrderNo(orderNo);
        return getOrderInfo(order);
    }

    private OrderInfo getOrderInfo(Order order) {
        OrderInfo orderInfo = new OrderInfo();
        List<OrderDetail> orderDetails = orderDetailMapper.queryByOrderNo(order.getOrderNo());

        orderInfo.setId(order.getId());
        orderInfo.setOrderNo(order.getOrderNo());
        orderInfo.setCreateUser(order.getCreateUser());
        for (OrderDetail orderDetail : orderDetails) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProductId(orderDetail.getProductId());
            productInfo.setProductName(orderDetail.getProductName());
            productInfo.setPrice(orderDetail.getPrice());
            productInfo.setNumber(orderDetail.getNumber());
            orderInfo.getProducts().add(productInfo);
        }
        return orderInfo;
    }
}

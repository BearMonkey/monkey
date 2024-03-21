package org.monkey.mqtx.order.service.impl;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.mqtx.common.enums.OrderState;
import org.monkey.mqtx.order.mapper.OrderMapper;
import org.monkey.mqtx.order.pojo.Order;
import org.monkey.mqtx.order.service.OrderService;
import org.monkey.mqtx.order.util.OrderUtil;
import org.monkey.mqtx.common.vo.OrderVo;
import org.monkey.mqtx.rabbitmq.service.config.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void createOrder(OrderVo orderVo) {
        log.info("start create order");
        // 参数检查
        if (null == orderVo) {
            log.info("订单信息为空");
            return;
        }
        // 生成订单编号
        orderVo.setOrderNo(OrderUtil.generateOrderNo(orderVo.getCreateUser()));

        // 查询订单是否存在，防止重复生成
        Order order = orderMapper.queryByOrderNo(orderVo.getOrderNo());
        if (order != null) {
            log.info("订单已存在.");
            return;
        }

        // 新增订单记录
        order = new Order();
        order.setOrderNo(orderVo.getOrderNo());
        order.setProductId(orderVo.getProduct().getProductId());
        order.setTotlePrice(OrderUtil.calcTotlePrice(orderVo));
        order.setCreateUser(order.getCreateUser());
        order.setOrderState(OrderState.NEW.getName());
        orderMapper.insert(order);

        // 给支付服务发消息完成扣费，没有支付系统，默认时成功的
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.ORDER_DIRECT_EXCHANGE,
                RabbitMqConfig.ORDER_PAY_ROUTING_KEY,
                orderVo);

        // 给商品服务发消息扣减库存
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.ORDER_DIRECT_EXCHANGE,
                RabbitMqConfig.ORDER_MODIFY_INVENTORY_ROUTING_KEY,
                orderVo);

    }

    //@RabbitListener(queues = RabbitMqConfig.ORDER_LOCKED_QUEUE)
    public void addOrder(String msg, Channel channel, Message message) {
        log.info("收到 order:locked 队列的消息，向订单表中插入订单数据, msg={}, message={}", msg, message);
        OrderVo orderVo = JsonUtil.strToObj(msg, OrderVo.class);
        if (null == orderVo) {
            log.error("从 order:locked 队列获取到的订单信息为空");
            // todo: 向order:fail 发送失败消息
            return;
        }
        Order order = orderMapper.queryByOrderNo(orderVo.getOrderNo());
        if (order != null) {
            log.info("订单已存在.");
            return;
        }
        order = new Order();
        order.setOrderNo(orderVo.getOrderNo());
        order.setProductId(orderVo.getProduct().getProductId());
        order.setTotlePrice(OrderUtil.calcTotlePrice(orderVo));
        order.setCreateUser(order.getCreateUser());
        order.setOrderState(OrderState.NEW.getName());
        orderMapper.insert(order);
        // 插入数据完成后，向 order:pay 发送消息，用户模块处理该队列完成支付流程
        rabbitTemplate.convertAndSend(
                RabbitMqConfig.ORDER_DIRECT_EXCHANGE,
                RabbitMqConfig.ORDER_PAY_ROUTING_KEY,
                orderVo
        );
    }
}

package org.monkey.mqtx.pay.service.impl;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.monkey.common.utils.JsonUtil;
import org.monkey.mqtx.common.enums.OrderState;
import org.monkey.mqtx.common.vo.OrderVo;
import org.monkey.mqtx.rabbitmq.service.config.RabbitMqConfig;
import org.monkey.mqtx.pay.service.PayService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PayServiceImpl implements PayService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMqConfig.ORDER_PAY_QUEUE)
    public void payOrder(String msg, Channel channel, Message message) {
        log.info("收到 order:pay 队列的消息，完成订单支付, msg={}, message={}", msg, message);
        // 参数处理
        OrderVo orderVo = JsonUtil.strToObj(msg, OrderVo.class);
        if (null == orderVo) {
            log.error("从 order:pay 队列获取到的订单信息为空");
            // todo: 向order:fail 发送失败消息
            return;
        }
        //
        // 调用支付接口完成支付
        if (true) {
            // 支付成功, 新增支付记录
        } else {
            // 支付失败

        }

    }
}

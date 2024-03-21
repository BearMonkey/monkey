package org.monkey.mqtx.rabbitmq.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMqConfig {

    /**
     * 交换机名称
     */
    public static final String ORDER_DIRECT_EXCHANGE = "OrderDirectExchange";

    /**
     * 新订单队列
     */
    public static final String ORDER_NEW_QUEUE = "order:new";

    /**
     * 新订单队列routingkey
     */
    public static final String ORDER_NEW_ROUTING_KEY = "order.new.routing.key";

    /**
     * 锁库订单队列
     */
    public static final String ORDER_LOCKED_QUEUE = "order:locked";

    /**
     * 锁库订单队列routingkey
     */
    public static final String ORDER_LOCKED_ROUTING_KEY = "order.locked.routing.key";

    /**
     * 锁库订单队列
     */
    public static final String ORDER_FAIL_QUEUE = "order:fail";

    /**
     * 锁库订单队列routingkey
     */
    public static final String ORDER_FAIL_ROUTING_KEY = "order.fail.routing.key";

    /**
     * 待支付订单队列
     */
    public static final String ORDER_PAY_QUEUE = "order:pay";

    /**
     * 待支付订单队列routingkey
     */
    public static final String ORDER_PAY_ROUTING_KEY = "order.pay.routing.key";

    /**
     * 待修改库存订单队列
     */
    public static final String ORDER_MODIFY_INVENTORY_QUEUE = "order:modify_inventory";

    /**
     * 待修改库存订单队列routingkey
     */
    public static final String ORDER_MODIFY_INVENTORY_ROUTING_KEY = "order.modify_inventory.routing.key";

    @Bean
    public DirectExchange orderDirectExchange() {
        return new DirectExchange(ORDER_DIRECT_EXCHANGE);
    }

    @Bean
    public Queue orderNewQueue() {
        // 死信交换机配置
        /*Map<String, Object> args = new HashMap<>(2);
        //交换机标识符
        args.put("x-dead-letter-exchange", "dead_direct_exchange");
        //绑定键标识符
        args.put("x-dead-letter-routing-key", "DeadRoutingKey");
        return new Queue("direct.queue.ack", true, false, false, args);*/
        return new Queue(ORDER_NEW_QUEUE, true);
    }

    @Bean
    public Binding orderNewQueueBindDirectExchange(Queue orderNewQueue, DirectExchange orderDirectExchange) {
        return BindingBuilder.bind(orderNewQueue).to(orderDirectExchange).with(ORDER_NEW_ROUTING_KEY);
    }
}

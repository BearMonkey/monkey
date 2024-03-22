package org.monkey.rabbitmq.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//@Configuration
public class DirectConfig {

    public static final String DIRECT_EXCHANGE = "direct_exchange";

    public static final String DIRECT_QUEUE_1 = "direct.queue1";

    public static final String DIRECT_QUEUE_2 = "direct.queue2";

    public static final String DIRECT_QUEUE_3 = "direct.queue3";

    public static final String DIRECT_ROUTING_KEY_1 = "direct.key.1111";

    public static final String DIRECT_ROUTING_KEY_2 = "direct.key.2222";

    public static final String DIRECT_ROUTING_KEY_3 = "direct.key.3333";

    public static final String DIRECT_ROUTING_KEY_4 = "direct.key.4444";

    public static final String DIRECT_QUEUE_FAIL = "direct.fail.queue";

    public static final String DIRECT_FAIL_ROUTING_KEY = "direct.fail.key";

    public static final String DIRECT_CONFIRM_ROUTING_KEY = "direct.key.confirm";

    /* 死信配置 */
    public static final String DIRECT_DEAD_EXCHANGE = "direct_dead_exchange";
    public static final String DIRECT_DEAD_QUEUE = "direct.dead.queue";
    public static final String DIRECT_DEAD_ROUTING_KEY = "direct.dead.key";

    /**
     * 交换机，bean名称同：directExchange
     *
     * @return directExchange
     */
    /*@Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }*/
    
    /**
     * 队列1
     *
     * @return Queue
     */
    /*@Bean
    public Queue directQueue1() {
        return new Queue(DIRECT_QUEUE_1);
    }*/
    
    /**
     * 队列2
     *
     * @return Queue
     */
    /*@Bean
    public Queue directQueue2() {
        return new Queue(DIRECT_QUEUE_2);
    }*/
    
    /**
     * 队列3
     *
     * @return Queue
     */
    /*@Bean
    public Queue directQueue3() {
        return new Queue(DIRECT_QUEUE_3);
    }*/
    
    /**
     * 队列1 绑定到交换机
     * 队列1 绑定了两个routingkey
     * @param directQueue1 队列1
     * @param directExchange 交换机
     * @return Binding对象
     */
    /*@Bean
    public Binding bindingDirectExchangeQueue1(Queue directQueue1, DirectExchange directExchange) {
        BindingBuilder.DirectExchangeRoutingKeyConfigurer to = BindingBuilder.bind(directQueue1).to(directExchange);
        to.with(DIRECT_ROUTING_KEY_1);
        return to.with(DIRECT_ROUTING_KEY_2);
        //return BindingBuilder.bind(directQueue1).to(directExchange).with("direct.key.1111");
    }*/
    
    /**
     * 队列2 绑定到交换机
     * 队列2 绑定了1个routingkey
     * @param directQueue2 队列2
     * @param directExchange 交换机
     * @return Binding对象
     */
    /*@Bean
    public Binding bindingDirectExchangeQueue2(Queue directQueue2, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue2).to(directExchange).with(DIRECT_ROUTING_KEY_3);
    }*/
    
    /**
     * 队列3 绑定到交换机
     * @param directQueue3 队列3
     * @param directExchange 交换机
     * @return Binding对象
     */
    /*@Bean
    public Binding bindingDirectExchangeQueue3(Queue directQueue3, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue3).to(directExchange).with(DIRECT_ROUTING_KEY_4);
    }*/




    /**
     * ack 测试队列
     *
     * @return Queue
     */
    /*@Bean
    public Queue directQueueAck() {
        Map<String, Object> args = new HashMap<>(2);
        //交换机标识符
        args.put("x-dead-letter-exchange", DIRECT_DEAD_EXCHANGE);
        //绑定键标识符
        args.put("x-dead-letter-routing-key", DIRECT_DEAD_ROUTING_KEY);
        return new Queue(DIRECT_QUEUE_ACK, true, false, false, args);
    }*/

    /**
     * ack 队列绑定交换机
     *
     * @param directQueueAck directQueueAck
     * @param directExchange directExchange
     * @return Binding
     */
    /*@Bean
    public Binding bindingAckQueueAndDirectExchange(Queue directQueueAck, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueueAck).to(directExchange).with(DIRECT_ACK_ROUTING_KEY);
    }*/

    /**
     * 死信交换机
     *
     * @return DirectExchange
     */
    /*@Bean
    public DirectExchange deadExchange() {
        return new DirectExchange(DIRECT_DEAD_EXCHANGE);
    }*/

    /**
     * 死信队列
     *
     * @return Queue
     */
    /*@Bean
    public Queue deadQueue() {
        return new Queue(DIRECT_DEAD_QUEUE, true);
    }*/

    /**
     * 死信队列和死信交换机绑定
     *
     * @param deadQueue 死信队列
     * @param deadExchange 死信交换机
     * @return Binding
     */
    /*@Bean
    public Binding bindingDeadQueueAndDeadExchange(Queue deadQueue, DirectExchange deadExchange) {
        return BindingBuilder.bind(deadQueue).to(deadExchange).with(DIRECT_DEAD_ROUTING_KEY);
    }*/
}

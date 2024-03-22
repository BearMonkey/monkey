package org.monkey.rabbitmq.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    public static final String FANOUT_EXCHANGE = "fanout_exchange";

    public static final String FANOUT_QUEUE_1 = "fanout.queue1";

    public static final String FANOUT_QUEUE_2 = "fanout.queue2";

    public static final String FANOUT_QUEUE_3 = "fanout.queue3";
    
    /**
     * 交换机，bean名称同：fanoutExchange
     *
     * @return FanoutExchange
     */
    /*@Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }*/
    
    /**
     * 队列1
     *
     * @return Queue
     */
    /*@Bean
    public Queue fanoutQueue1() {
        return new Queue(FANOUT_QUEUE_1);
    }*/
    
    /**
     * 队列2
     *
     * @return Queue
     */
    /*@Bean
    public Queue fanoutQueue2() {
        return new Queue(FANOUT_QUEUE_2);
    }*/
    
    /**
     * 队列1 绑定到交换机
     * @param fanoutQueue1 队列1
     * @param fanoutExchange 交换机
     * @return Binding对象
     */
    /*@Bean
    public Binding bindingFanoutExchangeQueue1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }*/
    
    /**
     * 队列2 绑定到交换机
     * @param fanoutQueue2 队列2
     * @param fanoutExchange 交换机
     * @return Binding对象
     */
    /*@Bean
    public Binding bindingFanoutExchangeQueue2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }*/
}

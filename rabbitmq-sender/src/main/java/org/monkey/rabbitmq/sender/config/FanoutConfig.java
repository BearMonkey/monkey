package org.monkey.rabbitmq.sender.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {
    
    /**
     * 交换机，bean名称同：fanoutExchange
     *
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_exchange");
    }
    
    /**
     * 队列1
     *
     * @return Queue
     */
    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanout.queue1");
    }
    
    /**
     * 队列2
     *
     * @return Queue
     */
    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanout.queue2");
    }
    
    /**
     * 队列1 绑定到交换机
     * @param fanoutQueue1 队列1
     * @param fanoutExchange 交换机
     * @return Binding对象
     */
    @Bean
    public Binding bindingFanoutExchangeQueue1(Queue fanoutQueue1, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange);
    }
    
    /**
     * 队列2 绑定到交换机
     * @param fanoutQueue2 队列2
     * @param fanoutExchange 交换机
     * @return Binding对象
     */
    @Bean
    public Binding bindingFanoutExchangeQueue2(Queue fanoutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange);
    }
}

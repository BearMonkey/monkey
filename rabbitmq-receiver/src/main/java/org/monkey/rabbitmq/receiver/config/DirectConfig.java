package org.monkey.rabbitmq.receiver.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {
    
    /**
     * 交换机，bean名称同：directExchange
     *
     * @return directExchange
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_exchange");
    }
    
    /**
     * 队列1
     *
     * @return Queue
     */
    @Bean
    public Queue directQueue1() {
        return new Queue("direct.queue1");
    }
    
    /**
     * 队列2
     *
     * @return Queue
     */
    @Bean
    public Queue directQueue2() {
        return new Queue("direct.queue2");
    }
    
    /**
     * 队列3
     *
     * @return Queue
     */
    @Bean
    public Queue directQueue3() {
        return new Queue("direct.queue3");
    }
    
    /**
     * 队列1 绑定到交换机
     * 队列1 绑定了两个routingkey
     * @param directQueue1 队列1
     * @param directExchange 交换机
     * @return Binding对象
     */
    @Bean
    public Binding bindingDirectExchangeQueue1(Queue directQueue1, DirectExchange directExchange) {
        BindingBuilder.DirectExchangeRoutingKeyConfigurer to = BindingBuilder.bind(directQueue1).to(directExchange);
        to.with("direct.key.1111");
        return to.with("direct.key.2222");
        //return BindingBuilder.bind(directQueue1).to(directExchange).with("direct.key.1111");
    }
    
    /**
     * 队列2 绑定到交换机
     * 队列2 绑定了1个routingkey
     * @param directQueue2 队列2
     * @param directExchange 交换机
     * @return Binding对象
     */
    @Bean
    public Binding bindingDirectExchangeQueue2(Queue directQueue2, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue2).to(directExchange).with("direct.key.3333");
    }
    
    /**
     * 队列3 绑定到交换机
     * @param directQueue3 队列3
     * @param directExchange 交换机
     * @return Binding对象
     */
    @Bean
    public Binding bindingDirectExchangeQueue3(Queue directQueue3, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue3).to(directExchange).with("direct.key.4444");
    }
}

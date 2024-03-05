package org.monkey.rabbitmq.receiver.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置方式  可以在配置类中集中配置，也可以在消费者的 @RabbitListener 注解中进行配置
 * 详细配置见 <code>org.monkey.rabbitmq.receiver.component.TopicReceiver<code/>
 */
//@Configuration
public class TopicConfig {
    
    /**
     * topic 交换机
     * @return TopicExchange
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic_exchange");
    }
    
    /**
     * 队列1
     *
     * @return Queue
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue("topic.queue1");
    }
    
    /**
     * 队列2
     *
     * @return Queue
     */
    @Bean
    public Queue topicQueue2() {
        return new Queue("topic.queue2");
    }
    
    /**
     * 队列1 绑定到交换机
     * 队列1 绑定了routingkey topic.even.*
     * @param topicQueue1 队列1
     * @param topicExchange 交换机
     * @return Binding对象
     */
    @Bean
    public Binding bindingTopicExchangeQueue1(Queue topicQueue1, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue1).to(topicExchange).with("topic.even.*");
    }
    
    /**
     * 队列1 绑定到交换机
     * 队列1 绑定了routingkey topic.even.*
     * @param topicQueue2 队列1
     * @param topicExchange 交换机
     * @return Binding对象
     */
    @Bean
    public Binding bindingTopicExchangeQueue2(Queue topicQueue2, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with("topic.odd.*");
    }
}

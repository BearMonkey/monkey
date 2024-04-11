package org.monkey.rabbitmq.common.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置方式  可以在配置类中集中配置，也可以在消费者的 @RabbitListener 注解中进行配置
 * 详细配置见 <code>org.monkey.rabbitmq.receiver.component.TopicReceiver<code/>
 */
//@Configuration
public class TopicConfig {

    public static final String TOPIC_EXCHANGE = "topic_exchange";
    public static final String TOPIC_QUEUE_1 = "topic.queue1";
    public static final String TOPIC_QUEUE_2 = "topic.queue2";
    public static final String TOPIC_EVEN_ROUTING_KEY = "topic.even.*";
    public static final String TOPIC_ODD_ROUTING_KEY = "topic.odd.*";

    /**
     * topic 交换机
     * @return TopicExchange
     */
    /*@Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }*/
    
    /**
     * 队列1
     *
     * @return Queue
     */
    /*@Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE_1);
    }*/
    
    /**
     * 队列2
     *
     * @return Queue
     */
    /*@Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE_2);
    }*/
    
    /**
     * 队列1 绑定到交换机
     * 队列1 绑定了routingkey topic.even.*
     * @param topicQueue1 队列1
     * @param topicExchange 交换机
     * @return Binding对象
     */
    /*@Bean
    public Binding bindingTopicExchangeQueue1(Queue topicQueue1, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue1).to(topicExchange).with(TOPIC_EVEN_ROUTING_KEY);
    }*/
    
    /**
     * 队列1 绑定到交换机
     * 队列1 绑定了routingkey topic.even.*
     * @param topicQueue2 队列1
     * @param topicExchange 交换机
     * @return Binding对象
     */
    /*@Bean
    public Binding bindingTopicExchangeQueue2(Queue topicQueue2, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with(TOPIC_ODD_ROUTING_KEY);
    }*/
}

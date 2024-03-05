package org.monkey.rabbitmq.sender.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    
    /**
     * topic 交换机
     * 生产者只需要将消息发送到交换机，因此配置交换机即可
     * @return TopicExchange
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic_exchange");
    }
}

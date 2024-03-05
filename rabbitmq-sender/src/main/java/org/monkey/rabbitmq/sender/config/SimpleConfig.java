package org.monkey.rabbitmq.sender.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleConfig {
    
    /**
     * 简单队列模型 只有一个队列
     * @return Queue
     */
    @Bean
    public Queue queue() {
        return new Queue("simple_queue");
    }
}

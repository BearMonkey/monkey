package org.monkey.rabbitmq.direct.producer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfig {

    @Bean
    public Queue queue() {
        return new Queue("direct_queue");
    }
}

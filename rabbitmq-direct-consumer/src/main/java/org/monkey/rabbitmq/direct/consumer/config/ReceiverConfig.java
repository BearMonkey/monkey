package org.monkey.rabbitmq.direct.consumer.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceiverConfig {

    @Bean
    public Queue queue() {
        return new Queue("direct_queue");
    }
}

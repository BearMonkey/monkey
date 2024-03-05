package org.monkey.rabbitmq.sender.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
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
}

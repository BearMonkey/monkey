package org.monkey.rabbitmq.receiver.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerQueueConfig {
    
    /**
     * 工作队列模型 只有一个队列，一个生产者多个消费者
     * @return Queue
     */
    @Bean(name = "work_queue")
    public Queue queue() {
        return new Queue("work_queue");
    }
}

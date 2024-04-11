package org.monkey.rabbitmq.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class WorkerQueueConfig {

    public static final String WORK_QUEUE = "working.queue";
    /**
     * 工作队列模型 只有一个队列，一个生产者多个消费者
     * @return Queue
     */
    /*@Bean(name = "workQueue")
    public Queue workQueue() {
        return new Queue(WORK_QUEUE);
    }*/
}

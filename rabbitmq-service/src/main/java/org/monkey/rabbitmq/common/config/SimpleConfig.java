package org.monkey.rabbitmq.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleConfig {

    /*@Autowired
    private RabbitAdmin rabbitAdmin;*/

    public static final String SIMPLE_QUEUE = "simple_queue";

    public static final String SIMPLE_QUEUE_WITH_ACK = "simple_queue_with_ack";

    /**
     * 简单队列模型 只有一个队列  不在这里创建了，因为是懒加载模式，初次启动会报错
     * @return Queue
     */
    /*@Bean
    public Queue simpleQueue() {
        *//*Queue queue = new Queue(SIMPLE_QUEUE);
        rabbitAdmin.declareQueue(queue);
        return queue;*//*
        return new Queue(SIMPLE_QUEUE);
    }*/

    /*@Bean
    public Queue simpleQueueWithAck() {
        *//*Queue queue = new Queue(SIMPLE_QUEUE_WITH_ACK);
        rabbitAdmin.declareQueue(queue);
        return queue;*//*
        return new Queue(SIMPLE_QUEUE_WITH_ACK);
    }*/
}

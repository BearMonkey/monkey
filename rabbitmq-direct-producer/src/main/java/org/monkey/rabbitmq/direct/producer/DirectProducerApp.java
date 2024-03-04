package org.monkey.rabbitmq.direct.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DirectProducerApp {
    public static void main(String[] args) {
        SpringApplication.run(DirectProducerApp.class, args);
    }
}

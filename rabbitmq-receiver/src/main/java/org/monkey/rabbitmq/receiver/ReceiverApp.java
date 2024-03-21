package org.monkey.rabbitmq.receiver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ReceiverApp {
    public static void main(String[] args) {
        SpringApplication.run(ReceiverApp.class, args);
    }
}

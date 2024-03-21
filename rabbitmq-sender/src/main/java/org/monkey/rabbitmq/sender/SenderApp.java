package org.monkey.rabbitmq.sender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SenderApp {
    public static void main(String[] args) {
        SpringApplication.run(SenderApp.class, args);
    }
}

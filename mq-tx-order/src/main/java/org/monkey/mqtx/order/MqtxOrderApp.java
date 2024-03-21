package org.monkey.mqtx.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MqtxOrderApp {
    public static void main(String[] args) {
        SpringApplication.run(MqtxOrderApp.class, args);
    }
}

package org.monkey.mqtx.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MqtxProductApp {
    public static void main(String[] args) {
        SpringApplication.run(MqtxProductApp.class, args);
    }
}

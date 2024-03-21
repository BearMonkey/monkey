package org.monkey.mqtx.pay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MqtxPayApp {
    public static void main(String[] args) {
        SpringApplication.run(MqtxPayApp.class, args);
    }
}
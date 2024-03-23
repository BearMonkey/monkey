package org.monkey.mqtx.agent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MqtxAgentApp {
    public static void main(String[] args) {
        SpringApplication.run(MqtxAgentApp.class, args);
    }
}

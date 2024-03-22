package org.monkey.mqtx.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("org.monkey.mqtx.account.mapper")
public class MqtxAccountApp {
    public static void main(String[] args) {
        SpringApplication.run(MqtxAccountApp.class, args);
    }
}

package org.monkey.twopc.boss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TwoPcBossApp {
    public static void main(String[] args) {
        SpringApplication.run(TwoPcBossApp.class, args);
    }
}

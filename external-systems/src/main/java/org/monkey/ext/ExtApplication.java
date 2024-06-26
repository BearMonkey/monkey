package org.monkey.ext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ExtApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExtApplication.class, args);
    }
}

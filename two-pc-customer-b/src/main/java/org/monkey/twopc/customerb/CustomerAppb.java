package org.monkey.twopc.customerb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CustomerAppb {
    public static void main(String[] args) {
        SpringApplication.run(CustomerAppb.class, args);
    }
}

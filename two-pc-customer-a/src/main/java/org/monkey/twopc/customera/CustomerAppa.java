package org.monkey.twopc.customera;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CustomerAppa {
    public static void main(String[] args) {
        SpringApplication.run(CustomerAppa.class, args);
    }
}

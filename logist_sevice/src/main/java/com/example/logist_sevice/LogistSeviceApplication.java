package com.example.logist_sevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
public class LogistSeviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogistSeviceApplication.class, args);
    }

}

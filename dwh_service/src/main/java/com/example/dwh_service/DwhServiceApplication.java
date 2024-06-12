package com.example.dwh_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DwhServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DwhServiceApplication.class, args);
    }

}

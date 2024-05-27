package com.example.discovery_service_gnivc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServiceGnivcApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServiceGnivcApplication.class, args);
    }

}

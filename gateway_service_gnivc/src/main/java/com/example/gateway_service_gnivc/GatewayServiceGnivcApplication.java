package com.example.gateway_service_gnivc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
public class GatewayServiceGnivcApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceGnivcApplication.class, args);
	}

}

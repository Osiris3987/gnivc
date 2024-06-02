package com.example.portal_service.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@RequiredArgsConstructor
public class HeaderInterceptorConfig1 {
    private final ObjectMapper objectMapper1;

    @Bean
    public UserContextInterceptor1 userContextInterceptor(UserContext1 userContext1) {
        return new UserContextInterceptor1(userContext1, objectMapper1);
    }

    @Bean
    @RequestScope
    public UserContext1 userContext(){
        return new UserContext1();
    }
}

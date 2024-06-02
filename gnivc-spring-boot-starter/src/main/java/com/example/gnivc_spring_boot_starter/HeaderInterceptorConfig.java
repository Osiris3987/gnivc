package com.example.portal_service.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
@RequiredArgsConstructor
public class HeaderInterceptorConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public UserContextInterceptor userContextInterceptor(UserContext userContext) {
        return new UserContextInterceptor(userContext, objectMapper);
    }

    @Bean
    @RequestScope
    public UserContext userContext(){
        return new UserContext();
    }
}

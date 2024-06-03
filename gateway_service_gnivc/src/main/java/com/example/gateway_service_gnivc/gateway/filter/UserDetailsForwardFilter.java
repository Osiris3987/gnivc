package com.example.gateway_service_gnivc.gateway.filter;

import com.example.gateway_service_gnivc.security.model.UserDetailsImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserDetailsForwardFilter implements GlobalFilter {
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return ReactiveSecurityContextHolder.getContext()
                .filter(c -> c.getAuthentication() != null)
                .flatMap(c -> {
                    UserDetailsImpl userDetails = (UserDetailsImpl) c.getAuthentication().getPrincipal();
                    ServerHttpRequest request = getModifiedRequest(exchange, userDetails);
                    return chain.filter(exchange.mutate().request(request).build());
                })
                .switchIfEmpty(chain.filter(exchange));
    }

    private ServerHttpRequest getModifiedRequest(ServerWebExchange exchange, UserDetailsImpl userDetails) {
        try {
            return exchange.getRequest().mutate()
                    .header("x-userId", userDetails.getUserId())
                    .header("x-roles", objectMapper.writeValueAsString(userDetails.getRoles()))
                    .build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

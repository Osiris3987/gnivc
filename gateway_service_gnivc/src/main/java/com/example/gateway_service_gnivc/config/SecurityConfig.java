package com.example.gateway_service_gnivc.config;

import com.example.gateway_service_gnivc.security.AccountAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
public class SecurityConfig {
    private final AccountAuthenticationProvider provider;


    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http.httpBasic(Customizer.withDefaults())
                .headers(headerSpec ->
                        headerSpec.contentSecurityPolicy(contentSecurityPolicySpec ->
                                contentSecurityPolicySpec.policyDirectives("upgrade-insecure-requests")))
                .cors(Customizer.withDefaults())
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(request ->
                        request
                                .pathMatchers("/portal/**").permitAll()
                                .pathMatchers("/logist/**").hasAuthority("LOGIST")
                                .pathMatchers("/openid-connect/**").permitAll()
                )
                .oauth2ResourceServer(resourceServerConfigurer -> resourceServerConfigurer
                        .authenticationManagerResolver(context -> Mono.just(provider)))
                .build();
    }
}

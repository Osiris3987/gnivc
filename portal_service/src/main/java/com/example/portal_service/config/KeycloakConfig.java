package com.example.portal_service.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    String serverUrl = "http://localhost:8080";
    String realm = "GatewayRealm";
    String clientId = "testClient";
    String clientSecret = "PsY7cOvwZn2aHTsYptvKSvEaqvTNg7g4";
    @Bean
    public Keycloak keycloakClient() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username("osiris3987")
                .password("oleg2004")
                .build();
    }

    @Bean
    public RealmResource realmResource() {
        return keycloakClient().realm("GatewayRealm");
    }
}

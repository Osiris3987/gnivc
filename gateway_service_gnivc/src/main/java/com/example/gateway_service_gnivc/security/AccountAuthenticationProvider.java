package com.example.gateway_service_gnivc.security;

import com.example.gateway_service_gnivc.security.model.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
@RequiredArgsConstructor
public class AccountAuthenticationProvider implements ReactiveAuthenticationManager {
    private static final String USERNAME_CLAIM = "preferred_username";
    private static final String ROLES_CLAIM = "roles";
    private static final String USER_ID_CLAIM = "sub";
    private static final String REALM_ACCESS_CLAIM = "realm_access";
    private final ReactiveJwtDecoder reactiveJwtDecoder;

    @Override
    @SneakyThrows
    public Mono<Authentication> authenticate(Authentication authentication) {
        BearerTokenAuthenticationToken token = (BearerTokenAuthenticationToken) authentication;
        Mono<Jwt> jwt1 = getJwt(token);
        Mono<UserDetailsImpl> userDetailsMono = jwt1.map(jwt -> {
            String userId = jwt.getClaimAsString(USER_ID_CLAIM);
            String username = jwt.getClaimAsString(USERNAME_CLAIM);
            return new UserDetailsImpl(userId, username, getRolesFromJwt(jwt));
        }).doOnError(Throwable::getMessage);
        return userDetailsMono.map(userDetails -> (Authentication) new UsernamePasswordAuthenticationToken(
                userDetails,
                token.getCredentials(),
                userDetails.getAuthorities()
        )).doOnError(Throwable::getMessage);
    }

    private List<String> getRolesFromJwt(Jwt jwt) {
        Map<String, Object> claim = Optional.ofNullable(jwt.getClaimAsMap(REALM_ACCESS_CLAIM)).orElse(new HashMap<>());
        List<String> rolesList = (List<String>) Optional.ofNullable(claim.get(ROLES_CLAIM)).orElse(new ArrayList<>());
        return rolesList;
    }

    private Mono<Jwt> getJwt(BearerTokenAuthenticationToken token) {
        String tokenToString = token.getToken();
        return this.reactiveJwtDecoder.decode(tokenToString).doOnError(Throwable::getMessage);
    }
}

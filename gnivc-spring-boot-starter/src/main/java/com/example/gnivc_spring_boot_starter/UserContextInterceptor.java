package com.example.gnivc_spring_boot_starter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserContextInterceptor implements HandlerInterceptor {

    private UserContext userContext;
    private final ObjectMapper objectMapper;
    private final CollectionType collectionType;

    public UserContextInterceptor(UserContext userContext, ObjectMapper objectMapper) {
        this.userContext = userContext;
        this.objectMapper = objectMapper;
        this.collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, String.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String roles = request.getHeader("x-roles");
        if(Objects.nonNull(roles)) {
            List<String> rolesValue = objectMapper.readValue(roles, collectionType);
            String userId = request.getHeader("x-userId");
            userContext.setRoles(rolesValue);
            userContext.setUserId(UUID.fromString(userId));
        }
        return true;
    }
}

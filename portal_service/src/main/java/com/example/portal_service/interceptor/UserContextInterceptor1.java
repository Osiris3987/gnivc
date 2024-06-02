package com.example.portal_service.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserContextInterceptor1 implements HandlerInterceptor {

    private UserContext1 userContext1;
    private final ObjectMapper objectMapper;
    private final CollectionType collectionType;

    public UserContextInterceptor1(UserContext1 userContext1, ObjectMapper objectMapper) {
        this.userContext1 = userContext1;
        this.objectMapper = objectMapper;
        this.collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, String.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String roles = request.getHeader("x-roles");
        if(Objects.nonNull(roles)) {
            List<String> rolesValue = objectMapper.readValue(roles, collectionType);
            String userId = request.getHeader("x-userId");
            userContext1.setRoles(rolesValue);
            userContext1.setUserId(UUID.fromString(userId));
        }
        return true;
    }
}

package com.example.portal_service.interceptor;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserContext1 {
    private List<String> roles;
    private UUID userId;
}

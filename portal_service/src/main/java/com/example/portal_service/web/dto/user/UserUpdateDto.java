package com.example.portal_service.web.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class UserUpdateDto {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}

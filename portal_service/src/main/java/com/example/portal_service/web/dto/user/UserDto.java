package com.example.portal_service.web.dto.user;

import lombok.Data;

@Data
public class UserDto {
    private String userId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}

package com.example.portal_service.web.mapper;

import com.example.portal_service.model.user.User;
import com.example.portal_service.web.dto.user.UserDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserRepresentation toRepresentation(UserDto dto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(dto.getFirstName());
        userRepresentation.setLastName(dto.getLastName());
        userRepresentation.setUsername(dto.getUsername());
        userRepresentation.setEmail(dto.getEmail());
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        return user;
    }
}

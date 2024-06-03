package com.example.portal_service.web.controller;

import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.portal_service.model.user.User;
import com.example.portal_service.service.DaDataService;
import com.example.portal_service.service.KeycloakService;
import com.example.portal_service.service.UserService;
import com.example.portal_service.web.dto.dadata_api.DaDataJsomArrayResponse;
import com.example.portal_service.web.dto.dadata_api.DaDataRequest;
import com.example.portal_service.web.dto.user.UserDto;
import com.example.portal_service.web.dto.user.UserUpdateDto;
import com.example.portal_service.web.mapper.UserMapper;
import com.example.portal_service.web.mapper.UserMapperMapstruct;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class PortalController {
    private final UserService userService;
    private final DaDataService daDataService;
    private final UserMapper userMapper;
    private final KeycloakService keycloakService;
    private final UserMapperMapstruct userMapperMapstruct;

    @PostMapping
    public String test2(@RequestBody UserDto dto) {
        return userService.createRegistratorUser(userMapper.toRepresentation(dto), userMapper.toEntity(dto));
    }

    @PutMapping
    public void updateUser(@RequestBody UserUpdateDto userUpdateDto) {
        User updatedUser = userMapperMapstruct.updateUser(
                userService.findById(userUpdateDto.getId()),
                userUpdateDto
                );
        UserRepresentation updateUserRepresentation = userMapperMapstruct.updateUserRepresentation(
                keycloakService.getUserRepresentation(userUpdateDto.getId().toString()),
                userUpdateDto
        );
        userService.updateUser(updateUserRepresentation, updatedUser);
    }

    @PostMapping("/updatePassword")
    public void updatePassword(@RequestParam String password){
        keycloakService.changePassword(password);
    }
    @GetMapping("/roles")
    public List<String> getAllRoles() {
        return keycloakService.getAllCompanyRoles();
    }
}

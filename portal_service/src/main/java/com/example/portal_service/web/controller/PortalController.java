package com.example.portal_service.web.controller;

import com.example.portal_service.interceptor.UserContext;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class PortalController {
    private final UserContext userContext;
    private final UserService userService;
    private final DaDataService daDataService;
    private final UserMapper userMapper;
    private final KeycloakService keycloakService;
    private final UserMapperMapstruct userMapperMapstruct;
    @GetMapping("/test")
    public String test() {
        return "hi from portal" + userContext.getUserId() + userContext.getRoles();
    }
    @GetMapping("/test1")
    public String test1() {
        return userService.getUser();
    }

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

    @GetMapping("/test3")
    public DaDataJsomArrayResponse test3(@RequestBody DaDataRequest dto){
        return daDataService.sendPostRequest("http://suggestions.dadata.ru/suggestions/api/4_1/rs/findById/party",
                dto);
    }
}

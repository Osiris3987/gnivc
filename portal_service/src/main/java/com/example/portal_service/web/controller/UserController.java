package com.example.portal_service.web.controller;

import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.portal_service.model.company.Company;
import com.example.portal_service.model.user.User;
import com.example.portal_service.service.CompanyService;
import com.example.portal_service.service.KeycloakService;
import com.example.portal_service.service.UserService;
import com.example.portal_service.web.dto.user.CompanyDriverRequest;
import com.example.portal_service.web.dto.user.UpdatePasswordRequest;
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
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final KeycloakService keycloakService;
    private final UserMapperMapstruct userMapperMapstruct;
    private final CompanyService companyService;
    private final UserContext userContext;

    @PostMapping
    public String createRegistratorUser(@RequestBody UserDto dto) {
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
    public void updatePassword(@RequestBody UpdatePasswordRequest request){
        keycloakService.changePassword(request.getPassword());
    }

    @GetMapping("/roles")
    public List<String> getAllRoles() {
        return keycloakService.getAllCompanyRoles();
    }

    @PostMapping("/driver")
    public UserDto getCompanyDriver(@RequestBody CompanyDriverRequest dto) {
        User user = userService.findById(dto.getDriverId());
        Company company = companyService.findById(dto.getCompanyId());
        return userMapperMapstruct.toDto(userService.findCompanyDriver(user, company));
    }
}

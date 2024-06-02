package com.example.portal_service.service;

import com.example.portal_service.interceptor.UserContext;
import com.example.portal_service.model.user.User;
import com.example.portal_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final Keycloak keycloak;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final KeycloakService keycloakService;
    private final UserContext userContext;
    private CompanyService companyService;

    @Autowired
    @Lazy
    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public String getUser() {
        return keycloak.realm("GatewayRealm").roles().get("notExistsing").toRepresentation().getName();
    }
    public String createRegistratorUser(UserRepresentation userRepresentation, User user) {
        RealmResource realmResource = keycloak.realm("GatewayRealm");
        UsersResource usersResource = realmResource.users();
        RoleRepresentation REGISTER = realmResource.roles().get("REGISTRATOR").toRepresentation();
        String password = PasswordGeneratorService.generatePassword();
        userRepresentation.setCredentials(List.of(keycloakService.generatePasswordRepresentation(password)));
        Response response = usersResource.create(userRepresentation);
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource resource = usersResource.get(CreatedResponseUtil.getCreatedId(response));
        resource.roles().realmLevel().add(Arrays.asList(REGISTER));
        userRepository.createUser(userId, user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        mailService.sendMail(user, password, new Properties());
        return password;
    }

    public String createCompanyUser(UserRepresentation userRepresentation, User user) {
        RealmResource realmResource = keycloak.realm("GatewayRealm");
        String password = PasswordGeneratorService.generatePassword();
        UsersResource usersResource = realmResource.users();
        userRepresentation.setCredentials(List.of(keycloakService.generatePasswordRepresentation(password)));
        Response response = usersResource.create(userRepresentation);
        userRepository.createUser(CreatedResponseUtil.getCreatedId(response), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        mailService.sendMail(user, password, new Properties());
        return CreatedResponseUtil.getCreatedId(response);
    }

    //обновить данные и в бд и в кейклоке
    public void updateUser(UserRepresentation userRepresentation, User updatedUser){
        updatedUser.setId(userContext.getUserId());
        userRepository.save(updatedUser);
        keycloakService.updateUser(userRepresentation);
    }

    public User findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow();
    }
    public Map<String, Long> findUsersWithRoleCount(String companyId) {
        return keycloakService.getCompanyMembersAmountByRole(
                userRepository.findAllUsersIdByCompanyId(companyId)
                        .stream().map(user -> user.getId().toString())
                        .collect(Collectors.toList())
        );
    }
    public List<User> findAllByCompanyId(UUID companyId) {
        return userRepository.findAllUsersIdByCompanyId(companyId.toString());
    }

    public Map<String, String> findUsersWithCompanyRoles(UUID companyId){
        String companyName = companyService.findById(companyId).getName();
        return findAllByCompanyId(companyId).stream()
                .collect(Collectors.toMap(User::getUsername, user -> keycloakService.getUserCompanyRole(user.getId().toString(), companyName)));
    }

}

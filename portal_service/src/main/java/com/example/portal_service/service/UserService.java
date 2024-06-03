package com.example.portal_service.service;


import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.portal_service.model.user.User;
import com.example.portal_service.repository.UserRepository;
import com.example.portal_service.util.CredentialPair;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
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

    public String createRegistratorUser(UserRepresentation userRepresentation, User user) {
        CredentialPair<String, String> creds = keycloakService.createKeycloakUser(userRepresentation);
        userRepository.createUser(creds.getFirstElement(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        mailService.sendMail(user, creds.getSecondElement(), new Properties());
        return creds.getSecondElement();
    }

    public String createCompanyUser(UserRepresentation userRepresentation, User user) {
        CredentialPair<String, String> creds = keycloakService.createCompanyUser(userRepresentation);
        userRepository.createUser(creds.getFirstElement(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        mailService.sendMail(user, creds.getSecondElement(), new Properties());
        return creds.getSecondElement();
    }

    public void updateUser(UserRepresentation userRepresentation, User updatedUser){
        updatedUser.setId(userContext.getUserId());
        keycloakService.updateUser(userRepresentation);
        userRepository.save(updatedUser);
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
                .collect(Collectors.toMap(User::getUsername, user -> keycloakService.getUserCompanyRole(companyName)));
    }

}

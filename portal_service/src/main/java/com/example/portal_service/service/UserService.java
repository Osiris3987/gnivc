package com.example.portal_service.service;


import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.portal_service.model.company.Company;
import com.example.portal_service.model.company.GenericCompanyRole;
import com.example.portal_service.model.exception.ResourceNotFoundException;
import com.example.portal_service.model.user.User;
import com.example.portal_service.repository.UserRepository;
import com.example.portal_service.util.CredentialPair;
import lombok.RequiredArgsConstructor;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public String createRegistratorUser(UserRepresentation userRepresentation, User user) {
        CredentialPair<String, String> creds = keycloakService.createKeycloakUser(userRepresentation);
        try {
            userRepository.createUser(creds.getFirstElement(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        } catch (Exception e) {
            keycloakService.deleteUserById(creds.getFirstElement());
            throw e;
        }
        mailService.sendMail(user, creds.getSecondElement(), new Properties());
        return creds.getSecondElement();
    }

    @Transactional
    public String createCompanyUser(UserRepresentation userRepresentation, User user) {
        CredentialPair<String, String> creds = keycloakService.createCompanyUser(userRepresentation);
        try {
            userRepository.createUser(creds.getFirstElement(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        } catch (Exception e) {
            keycloakService.deleteUserById(creds.getFirstElement());
            throw e;
        }
        mailService.sendMail(user, creds.getSecondElement(), new Properties());
        return creds.getFirstElement();
    }

    @Transactional
    public void updateUser(UserRepresentation userRepresentation, User updatedUser) {
        UserRepresentation beforeUpdate = keycloakService.updateUser(userRepresentation);
        try {
            updatedUser.setId(userContext.getUserId());
            userRepository.save(updatedUser);
        } catch (Exception e) {
            keycloakService.updateUser(beforeUpdate);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public User findById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public Map<String, Long> findUsersWithRoleCount(String companyId) {
        return keycloakService.getCompanyMembersAmountByRole(
                userRepository.findAllUsersIdByCompanyId(companyId)
                        .stream().map(user -> user.getId().toString())
                        .collect(Collectors.toList())
        );
    }

    @Transactional(readOnly = true)
    public List<User> findAllByCompanyId(UUID companyId) {
        return userRepository.findAllUsersIdByCompanyId(companyId.toString());
    }

    @Transactional(readOnly = true)
    public Map<String, String> findUsersWithCompanyRoles(UUID companyId){
        String companyName = companyService.findById(companyId).getName();
        return findAllByCompanyId(companyId).stream()
                .collect(Collectors.toMap(User::getUsername, user -> keycloakService.getUserCompanyRole(companyName)));
    }

    public User findCompanyDriver(User user, Company company) {
        keycloakService.userHasDriverRole(user.getId().toString(), company.getName());
        keycloakService.userHasCurrentRole(List.of(GenericCompanyRole.LOGIST_.name() + company.getName()));
        return user;
    }

}

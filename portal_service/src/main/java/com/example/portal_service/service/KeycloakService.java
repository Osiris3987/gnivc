package com.example.portal_service.service;


import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.portal_service.model.company.GenericCompanyRole;
import com.example.portal_service.model.exception.AccessDeniedException;
import com.example.portal_service.util.CredentialPair;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final RealmResource realmResource;
    private final UserContext userContext;

    public void changePassword(String password) {
        UserRepresentation userRepresentation = getUserRepresentation(userContext.getUserId().toString());
        userRepresentation.setCredentials(List.of(generatePasswordRepresentation(password)));
        realmResource.users().get(userContext.getUserId().toString()).update(userRepresentation);
    }
    public UserRepresentation updateUser(UserRepresentation userRepresentation) {
        UserRepresentation beforeUpdate = realmResource.users().get(userContext.getUserId().toString()).toRepresentation();
        userRepresentation.setId(userContext.getUserId().toString());
        realmResource.users().get(userRepresentation.getId()).update(userRepresentation);
        return beforeUpdate;
    }

    public Map<String, Long> getCompanyMembersAmountByRole(List<String> ids){
        return ids.stream().map(id ->
                realmResource
                        .users()
                        .get(id)
                        .roles()
                        .realmLevel()
                        .listAll())
                .flatMap(Collection::stream)
                .filter(role ->
                                role.getName().startsWith(GenericCompanyRole.LOGIST_.name())
                                        || role.getName().startsWith(GenericCompanyRole.DRIVER_.name())
                )
                .collect(Collectors.groupingBy(RoleRepresentation::getName, Collectors.counting()));
    }

    public String getUserCompanyRole(String companyName) {
        return realmResource
                .users()
                .get(userContext.getUserId().toString())
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .filter(name -> name.contains(companyName))
                .findFirst().get();
    }

    public UserRepresentation getUserRepresentation(String userId) {
        return realmResource.users().get(userId).toRepresentation();
    }

    public CredentialPair<String, String> createKeycloakUser(UserRepresentation userRepresentation) {
        UsersResource usersResource = realmResource.users();
        RoleRepresentation REGISTER = realmResource.roles().get("REGISTRATOR").toRepresentation();
        String password = PasswordGeneratorService.generatePassword();
        userRepresentation.setCredentials(List.of(generatePasswordRepresentation(password)));
        Response response = usersResource.create(userRepresentation);
        UserResource resource = usersResource.get(CreatedResponseUtil.getCreatedId(response));
        resource.roles().realmLevel().add(Collections.singletonList(REGISTER));
        return new CredentialPair<>(
                CreatedResponseUtil.getCreatedId(response),
                password
        );
    }

    public CredentialPair<String, String> createCompanyUser(UserRepresentation userRepresentation) {
        String password = PasswordGeneratorService.generatePassword();
        UsersResource usersResource = realmResource.users();
        userRepresentation.setCredentials(List.of(generatePasswordRepresentation(password)));
        Response response = usersResource.create(userRepresentation);
        return new CredentialPair<>(
                CreatedResponseUtil.getCreatedId(response),
                password
        );
    }

    public List<RoleRepresentation> assignRolesForCreatedCompany(String companyName){
        RoleRepresentation admin = new RoleRepresentation(
                GenericCompanyRole.ADMIN_.name() + companyName, "", false
        );
        RoleRepresentation logist = new RoleRepresentation(
                GenericCompanyRole.LOGIST_.name() + companyName, "", false
        );
        RoleRepresentation driver = new RoleRepresentation(
                GenericCompanyRole.DRIVER_.name() + companyName, "", false
        );
        List.of(admin, logist, driver).forEach(role -> realmResource.roles().create(role));
        return List.of(admin, logist, driver);
    }

    public UserResource getUserResource() {
        return realmResource.users().get(userContext.getUserId().toString());
    }

    public void userHasCurrentRole(List<String> neededRoles) {
        boolean result = realmResource.users()
                .get(userContext.getUserId().toString())
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .anyMatch(neededRoles::contains);
        if (!result) throw new AccessDeniedException();
    }

    public void hasCurrentGenericRole(GenericCompanyRole role) {
        boolean result = realmResource.users()
                .get(userContext.getUserId().toString())
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .anyMatch(keycloakRole -> keycloakRole.startsWith(role.name()));
        if (!result) throw new AccessDeniedException();
    }

    public void hasPermissionForAssigningUser(
            GenericCompanyRole assignationRole,
            String companyName
    ) {
        switch (assignationRole) {

            case ADMIN_, LOGIST_  ->
                    userHasCurrentRole(
                            List.of(
                                    getCompositeRole(GenericCompanyRole.ADMIN_, companyName)
                            )
                    );

            case DRIVER_ ->
                    userHasCurrentRole(
                            List.of(
                                getCompositeRole(GenericCompanyRole.ADMIN_, companyName),
                                getCompositeRole(GenericCompanyRole.LOGIST_, companyName)
                            )
                    );
        }
    }

    public String getCompositeRole(GenericCompanyRole role, String companyName) {
        return role.name() + companyName;
    }

    public RoleRepresentation assignRoleToUser(String userId, GenericCompanyRole role, String companyName) {
        RoleRepresentation roleRepresentation = getRoleRepresentation(role.name() + companyName);
        realmResource.users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(
                        List.of(roleRepresentation)
                );

        if(!realmResource.users().get(userId).roles().realmLevel().listAll().contains(realmResource.roles().get(role.name().replace("_", "")).toRepresentation())) {
            realmResource.users().get(userId).roles().realmLevel().add(List.of(realmResource.roles().get(role.name().replace("_", "")).toRepresentation()));
        }
        return roleRepresentation;
    }

    public void deleteUserRole(RoleRepresentation roleRepresentation, String userId) {
        realmResource.users().get(userId).roles().realmLevel().remove(List.of(roleRepresentation));
    }

    public void deleteUserById(String userId) {
        realmResource.users().delete(userId);
    }

    public RoleRepresentation getRoleRepresentation(String roleName) {
        return realmResource.roles().get(roleName).toRepresentation();
    }

    public CredentialRepresentation generatePasswordRepresentation(String password){
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);
        return passwordCred;
    }

    public List<String> getAllCompanyRoles() {
        return realmResource
                .roles()
                .list()
                .stream()
                .map(RoleRepresentation::getName)
                .filter(role ->
                        role.startsWith(GenericCompanyRole.ADMIN_.name()) ||
                        role.startsWith(GenericCompanyRole.LOGIST_.name()) ||
                        role.startsWith(GenericCompanyRole.DRIVER_.name()))
                .toList();
    }

    public void userHasDriverRole(String userId, String companyName) {
        String neededRole = GenericCompanyRole.DRIVER_.name() + companyName;
        boolean result = realmResource.users()
                .get(userId)
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .anyMatch(role -> role.equals(neededRole));
        if (!result) throw new AccessDeniedException();
    }
}

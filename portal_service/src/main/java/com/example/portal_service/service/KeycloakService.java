package com.example.portal_service.service;

import com.example.portal_service.interceptor.UserContext1;
import com.example.portal_service.model.company.GenericCompanyRole;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
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
    private final UserContext1 userContext;

    public void changePassword(String password) {
        UserRepresentation userRepresentation = getUserRepresentation(userContext.getUserId().toString());
        userRepresentation.setCredentials(List.of(generatePasswordRepresentation(password)));
        realmResource.users().get(userContext.getUserId().toString()).update(userRepresentation);
    }
    public void updateUser(UserRepresentation userRepresentation) {
        userRepresentation.setId(userContext.getUserId().toString());
        realmResource.users().get(userRepresentation.getId()).update(userRepresentation);
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

    public String getUserCompanyRole(String userId, String companyName) {
        return realmResource
                .users()
                .get(userId)
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

    //TODO перенести userId во внутрь используя userContext
    public void userHasCurrentRole(String userId, List<String> neededRoles) {
        boolean result = realmResource.users()
                .get(userId)
                .roles()
                .realmLevel()
                .listAll()
                .stream()
                .map(RoleRepresentation::getName)
                .anyMatch(neededRoles::contains);
        if (!result) throw new RuntimeException("access denied");
    }

    public void hasPermissionForAssigningUser(
            String userId,
            GenericCompanyRole assignationRole,
            String companyName
    ) {
        switch (assignationRole) {

            case ADMIN_, LOGIST_  ->
                    userHasCurrentRole(
                            userId,
                            List.of(
                                    getCompositeRole(GenericCompanyRole.ADMIN_, companyName)
                            )
                    );

            case DRIVER_ ->
                    userHasCurrentRole(
                            userId,
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

    public void assignRoleToUser(String userId, GenericCompanyRole role, String companyName) {
        realmResource.users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(
                        List.of(getRoleRepresentation(role.name() + companyName))
                );
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
}

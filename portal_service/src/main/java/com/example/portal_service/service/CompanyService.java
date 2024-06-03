package com.example.portal_service.service;

import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.portal_service.model.company.Company;
import com.example.portal_service.model.company.GenericCompanyRole;
import com.example.portal_service.model.user.User;
import com.example.portal_service.repository.CompanyRepository;
import com.example.portal_service.web.dto.company.AssignRegisteredUserToCompanyRequest;
import com.example.portal_service.web.dto.dadata_api.DaDataRequest;
import com.example.portal_service.web.dto.dadata_api.DaDataResponse;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserContext userContext;
    private final DaDataService daDataService;
    private final UserService userService;
    private final Keycloak keycloak;
    private final KeycloakService keycloakService;

    public Company findById(UUID id) {
        Company company = companyRepository.findById(id).orElseThrow();
        keycloakService.userHasCurrentRole(List.of(
                GenericCompanyRole.ADMIN_.name() + company.getName())
        );
        return company;
    }

    public List<Company> findAll(int offset, int limit) {
        keycloakService.hasCurrentGenericRole(GenericCompanyRole.ADMIN_);
        return companyRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public Company findByName(String name) {
        return companyRepository.findByName(name).orElseThrow();
    }

    public void createCompany(Company company) {
        RealmResource realmResource = keycloak.realm("GatewayRealm");
        UserResource userRepresentation = realmResource.users().get(userContext.getUserId().toString());
        company.setOwner(userContext.getUserId().toString());
        User user = userService.findById(userContext.getUserId());
        company.setUsers(Set.of(user));
        companyRepository.save(company);
        keycloakService.assignRolesForCreatedCompany(company.getName());
        userRepresentation.roles().realmLevel().add(
                List.of(realmResource.roles()
                        .get(GenericCompanyRole.ADMIN_.name() + company.getName())
                        .toRepresentation())
        );
    }

    public void assignRegisteredUserToCompany(AssignRegisteredUserToCompanyRequest dto) {

        keycloakService.hasPermissionForAssigningUser(
                dto.getRole(),
                dto.getCompanyName()
        );

        User user = userService.findById(UUID.fromString(dto.getUserId()));
        Company company = companyRepository.findByName(dto.getCompanyName()).orElseThrow();
        company.getUsers().add(user);
        companyRepository.save(company);
        keycloakService.assignRoleToUser(dto.getUserId(), dto.getRole(), dto.getCompanyName());

    }

    public void assignUnregisteredUserToCompany(
            GenericCompanyRole role,
            UserRepresentation userRepresentation,
            User user,
            String companyName
    ) {

        Company company = companyRepository.findByName(companyName).orElseThrow();

        keycloakService.hasPermissionForAssigningUser(
                role,
                companyName
        );

        String userId = userService.createCompanyUser(userRepresentation, user);
        company.getUsers().add(userService.findById(UUID.fromString(userId)));
        companyRepository.save(company);
        keycloakService.assignRoleToUser(userId, role, companyName);
    }
}

package com.example.portal_service.service;

import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.portal_service.model.company.Company;
import com.example.portal_service.model.company.GenericCompanyRole;
import com.example.portal_service.model.exception.ResourceNotFoundException;
import com.example.portal_service.model.user.User;
import com.example.portal_service.repository.CompanyRepository;
import com.example.portal_service.web.dto.company.AssignRegisteredUserToCompanyRequest;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserContext userContext;
    private final UserService userService;
    private final KeycloakService keycloakService;
    private final RealmResource realmResource;

    @Transactional(readOnly = true)
    public Company findById(UUID id) {
        Company company = companyRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Company not found"));
        keycloakService.userHasCurrentRole(List.of(
                GenericCompanyRole.ADMIN_.name() + company.getName())
        );
        return company;
    }

    @Transactional(readOnly = true)
    public List<Company> findAll(int offset, int limit) {
        keycloakService.hasCurrentGenericRole(GenericCompanyRole.ADMIN_);
        return companyRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    @Transactional(readOnly = true)
    public Company findByName(String name) {
        return companyRepository.findByName(name).orElseThrow(()
                -> new ResourceNotFoundException("Company not found"));
    }

    @Transactional
    public void createCompany(Company company) {
        List<RoleRepresentation> representations = keycloakService.assignRolesForCreatedCompany(company.getName());;
        UserResource userResource = keycloakService.getUserResource();
        try {
            company.setOwner(userContext.getUserId().toString());
            User user = userService.findById(userContext.getUserId());
            company.setUsers(Set.of(user));
            companyRepository.save(company);
            userResource.roles().realmLevel().add(
                    List.of(realmResource.roles()
                            .get(GenericCompanyRole.ADMIN_.name() + company.getName())
                            .toRepresentation())
            );
        } catch (Exception e) {
            userResource.roles().realmLevel().remove(List.of(realmResource.roles()
                    .get(GenericCompanyRole.ADMIN_.name() + company.getName())
                    .toRepresentation()));
            representations.forEach(roleRepresentation -> realmResource.roles().deleteRole(roleRepresentation.getName()));
            throw e;
        }
    }

    @Transactional
    public void assignRegisteredUserToCompany(AssignRegisteredUserToCompanyRequest dto) {

        keycloakService.hasPermissionForAssigningUser(
                dto.getRole(),
                dto.getCompanyName()
        );

        RoleRepresentation roleRepresentation = keycloakService.assignRoleToUser(dto.getUserId(),
                dto.getRole(),
                dto.getCompanyName()
        );

        try {
            User user = userService.findById(UUID.fromString(dto.getUserId()));
            Company company = companyRepository.findByName(dto.getCompanyName()).orElseThrow();
            company.getUsers().add(user);
            companyRepository.save(company);
        } catch (Exception e) {
            keycloakService.deleteUserRole(roleRepresentation, dto.getUserId());
            throw e;
        }
    }

    @Transactional
    public void assignUnregisteredUserToCompany(
            GenericCompanyRole role,
            UserRepresentation userRepresentation,
            User user,
            String companyName
    ) {

        Company company = findByName(companyName);

        keycloakService.hasPermissionForAssigningUser(
                role,
                companyName
        );

        String userId = userService.createCompanyUser(userRepresentation, user);
        try {
            keycloakService.assignRoleToUser(userId, role, companyName);
            company.getUsers().add(userService.findById(UUID.fromString(userId)));
            companyRepository.save(company);
        } catch (Exception e) {
            keycloakService.deleteUserById(userId);
            throw e;
        }
    }
}

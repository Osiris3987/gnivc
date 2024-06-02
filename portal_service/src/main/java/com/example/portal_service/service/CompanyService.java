package com.example.portal_service.service;

import com.example.portal_service.interceptor.UserContext1;
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
    private final UserContext1 userContext;
    private final DaDataService daDataService;
    private final UserService userService;
    private final Keycloak keycloak;
    private final KeycloakService keycloakService;

    public Company findById(UUID id){
        return companyRepository.findById(id).orElseThrow();
    }

    public List<Company> findAll(int offset, int limit) {
        return companyRepository.findAll(PageRequest.of(offset, limit)).getContent();
    }

    public Company findByName(String name) {
        return companyRepository.findByName(name).orElseThrow();
    }

    public void createCompany(DaDataRequest dto) {
        RealmResource realmResource = keycloak.realm("GatewayRealm");
        UserResource userRepresentation = realmResource.users().get(userContext.getUserId().toString());
        Company company = new Company();
        DaDataResponse response = daDataService.sendPostRequest("http://suggestions.dadata.ru/suggestions/api/4_1/rs/findById/party", dto).getSuggestions()[0];

        company.setOgrn(response.getData().getOgrn());
        company.setKpp(response.getData().getKpp());
        company.setInn(response.getData().getInn());
        company.setAddress(response.getData().getAddress().getValue());
        company.setOwner(userContext.getUserId().toString());
        User user = userService.findById(userContext.getUserId());
        company.setUsers(Set.of(user));
        company.setName(response.getValue());
        companyRepository.save(company);
        assignRolesForCreatedCompany(response.getValue());
        userRepresentation.roles().realmLevel().add(List.of(realmResource.roles().get(GenericCompanyRole.ADMIN_.name() + response.getValue()).toRepresentation()));
    }
    public void assignRolesForCreatedCompany(String companyName){
        RoleRepresentation admin = new RoleRepresentation(
                GenericCompanyRole.ADMIN_.name() + companyName, "", false
        );
        RoleRepresentation logist = new RoleRepresentation(
                GenericCompanyRole.LOGIST_.name() + companyName, "", false
        );
        RoleRepresentation driver = new RoleRepresentation(
                GenericCompanyRole.DRIVER_.name() + companyName, "", false
        );
        RealmResource realmResource = keycloak.realm("GatewayRealm");
        List.of(admin, logist, driver).forEach(role -> realmResource.roles().create(role));
    }

    public void assignRegisteredUserToCompany(AssignRegisteredUserToCompanyRequest dto) {

        keycloakService.hasPermissionForAssigningUser(
                userContext.getUserId().toString(),
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
                userContext.getUserId().toString(),
                role,
                companyName
        );

        String userId = userService.createCompanyUser(userRepresentation, user);
        company.getUsers().add(userService.findById(UUID.fromString(userId)));
        companyRepository.save(company);
        keycloakService.assignRoleToUser(userId, role, companyName);
    }
}

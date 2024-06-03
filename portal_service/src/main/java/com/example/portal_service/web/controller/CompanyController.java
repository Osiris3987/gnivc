package com.example.portal_service.web.controller;

import com.example.portal_service.model.company.Company;
import com.example.portal_service.service.CompanyService;
import com.example.portal_service.service.DaDataService;
import com.example.portal_service.service.UserService;
import com.example.portal_service.web.dto.company.AssignRegisteredUserToCompanyRequest;
import com.example.portal_service.web.dto.company.AssignUnregisteredUserToCompanyRequest;
import com.example.portal_service.web.dto.company.CompanyResponse;
import com.example.portal_service.web.dto.company.CompanyWithUsersResponse;
import com.example.portal_service.web.dto.dadata_api.DaDataRequest;
import com.example.portal_service.web.dto.dadata_api.DaDataResponse;
import com.example.portal_service.web.mapper.CompanyMapper;
import com.example.portal_service.web.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;
    private final UserMapper userMapper;
    private final CompanyMapper companyMapper;
    private final UserService userService;
    private final DaDataService daDataService;

    @PostMapping
    public void createCompany(@RequestBody DaDataRequest dto){
        Company company = companyMapper.toCompany(daDataService.sendPostRequest(dto).getSuggestions()[0]);
        companyService.createCompany(company);
    }
    @PostMapping("/assign/registered")
    public void assignRegisteredUserToCompany(@RequestBody AssignRegisteredUserToCompanyRequest dto){
        companyService.assignRegisteredUserToCompany(dto);
    }
    @PostMapping("/assign/unregistered")
    public void assignUnregisteredUserToCompany(@RequestBody AssignUnregisteredUserToCompanyRequest dto) {
        companyService.assignUnregisteredUserToCompany(
                dto.getRole(),
                userMapper.toRepresentation(dto.getUser()),
                userMapper.toEntity(dto.getUser()),
                dto.getCompanyName()
                );
    }
    @GetMapping("/{id}")
    public CompanyWithUsersResponse getCompanyWithUsers(@PathVariable UUID id) {
        CompanyWithUsersResponse response = companyMapper.toCompanyWithUsersResponse(companyService.findById(id));
        response.setCompanyMembersWithRoles(userService.findUsersWithRoleCount(id.toString()));
        return response;
    }

    @GetMapping("/{companyId}/users")
    public Map<String, String> getUsersWithRolesByCompanyId(@PathVariable UUID companyId) {
        return userService.findUsersWithCompanyRoles(companyId);
    }

    @GetMapping
    public List<CompanyResponse> getAll(@RequestParam(required = false, defaultValue = "0") int offset,
                                        @RequestParam(required = false, defaultValue = "1") int limit){
        return companyMapper.toCompanyResponse(companyService.findAll(offset, limit));
    }

    /*
    @GetMapping("/{companyId}/users")
    public Map<String, Long> getUsersWithRoles(@PathVariable String companyId) {
        return userService.findUsersWithRoleCount(companyId);
    }
     */

}

package com.example.portal_service.web.controller;

import com.example.portal_service.model.company.Company;
import com.example.portal_service.model.company.GenericCompanyRole;
import com.example.portal_service.service.CompanyService;
import com.example.portal_service.service.KeycloakService;
import com.example.portal_service.web.dto.company.CompanyAccessRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authorization")
public class KeycloakController {

    private final KeycloakService keycloakService;
    private final CompanyService companyService;

    @PostMapping("/companyAccess")
    public void canAccessCompany(@RequestBody CompanyAccessRequest request) {
        Company company = companyService.findById(request.getCompanyId());
        keycloakService.userHasCurrentRole(List.of(GenericCompanyRole.DRIVER_ + company.getName(),
                GenericCompanyRole.LOGIST_.name() + company.getName()));
    }
}

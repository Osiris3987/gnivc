package com.example.portal_service.web.controller;

import com.example.portal_service.service.CompanyService;
import com.example.portal_service.web.dto.company.AssignRegisteredUserToCompanyRequest;
import com.example.portal_service.web.dto.dadata_api.DaDataRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    public void createCompany(@RequestBody DaDataRequest dto){
        companyService.createCompany(dto);
    }
    @PostMapping("assign/registered")
    public void assignRegisteredUserToCompany(@RequestBody AssignRegisteredUserToCompanyRequest dto){
        companyService.assignRegisteredUserToCompany(dto);
    }
}

package com.example.portal_service.web.dto.company;

import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class CompanyWithUsersResponse {
    private UUID id;
    private String inn;
    private String address;
    private String kpp;
    private String ogrn;
    private String name;
    private Map<String, Long> companyMembersWithRoles;
}

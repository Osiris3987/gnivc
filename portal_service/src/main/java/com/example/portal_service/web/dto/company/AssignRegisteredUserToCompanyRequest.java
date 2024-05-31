package com.example.portal_service.web.dto.company;

import com.example.portal_service.model.company.GenericCompanyRole;
import lombok.Data;

@Data
public class AssignRegisteredUserToCompanyRequest {
    private GenericCompanyRole role;
    private String userId;
    private String companyName;
}

package com.example.portal_service.web.dto.company;

import com.example.portal_service.model.company.GenericCompanyRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignRegisteredUserToCompanyRequest {
    private GenericCompanyRole role;
    private String userId;
    private String companyName;
}

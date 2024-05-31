package com.example.portal_service.web.dto.company;

import com.example.portal_service.model.company.GenericCompanyRole;
import com.example.portal_service.web.dto.user.UserDto;
import lombok.Data;

@Data
public class AssignUnregisteredUserToCompanyRequest {
    private GenericCompanyRole role;
    private String companyName;
    private UserDto user;
}

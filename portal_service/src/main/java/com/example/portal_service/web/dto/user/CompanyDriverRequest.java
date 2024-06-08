package com.example.portal_service.web.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class CompanyDriverRequest {
    private UUID driverId;
    private UUID companyId;
}

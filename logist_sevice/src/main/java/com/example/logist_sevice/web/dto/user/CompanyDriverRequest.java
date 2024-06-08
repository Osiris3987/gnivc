package com.example.logist_sevice.web.dto.user;

import lombok.Data;

import java.util.UUID;

@Data
public class CompanyDriverRequest {
    private UUID driverId;
    private UUID companyId;
    private UUID senderId;
}

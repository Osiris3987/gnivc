package com.example.portal_service.web.dto.transport;

import lombok.Data;

import java.util.UUID;

@Data
public class TransportResponse {
    private UUID id;

    private String vin;

    private Integer year;

    private UUID companyId;
}

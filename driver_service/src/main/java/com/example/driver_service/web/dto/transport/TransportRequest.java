package com.example.driver_service.web.dto.transport;

import lombok.Data;

import java.util.UUID;

@Data
public class TransportRequest {
    private String vin;

    private UUID companyId;
}

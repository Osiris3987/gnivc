package com.example.portal_service.web.dto.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class TransportRequest {
    private String vin;

    private Integer year;

    private UUID companyId;
}

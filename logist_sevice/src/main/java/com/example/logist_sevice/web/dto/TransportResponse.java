package com.example.logist_sevice.web.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TransportResponse {
    private UUID id;

    private String vin;

    private Integer year;
    private String stateNumber;

    private UUID companyId;
}

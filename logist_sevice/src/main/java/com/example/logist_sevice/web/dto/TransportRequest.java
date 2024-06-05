package com.example.logist_sevice.web.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TransportRequest {
    private String vin;

    private UUID companyId;
}

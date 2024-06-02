package com.example.portal_service.web.dto.company;

import lombok.Data;

import java.util.UUID;

@Data
public class CompanyResponse {

    private UUID id;

    private String inn;

    private String name;
}

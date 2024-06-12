package com.example.driver_service.web.dto.task;

import lombok.Data;

import java.util.UUID;

@Data
public class TaskResponse {

    private UUID id;

    private String startPoint;

    private String endPoint;

    private String driverFirstName;

    private String driverLastName;

    private String description;

    private String transportStateNumber;

    private String companyId;
}

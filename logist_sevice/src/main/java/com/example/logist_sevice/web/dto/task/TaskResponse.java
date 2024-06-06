package com.example.logist_sevice.web.dto.task;

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

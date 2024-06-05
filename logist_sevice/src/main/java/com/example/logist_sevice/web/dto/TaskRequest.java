package com.example.logist_sevice.web.dto;
import lombok.Data;

@Data
public class TaskRequest {
    private String startPoint;
    private String endPoint;
    private String description;
    private TransportRequest transport;
    private CompanyDriverRequest driver;
}

package com.example.driver_service.web.dto.task;
import com.example.driver_service.web.dto.transport.TransportRequest;
import com.example.driver_service.web.dto.user.CompanyDriverRequest;
import lombok.Data;

@Data
public class TaskRequest {
    private String startPoint;
    private String endPoint;
    private String description;
    private String companyId;
    private TransportRequest transport;
    private CompanyDriverRequest driver;
}

package com.example.logist_sevice.config;

import com.example.logist_sevice.web.dto.TransportRequest;
import com.example.logist_sevice.web.dto.TransportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "transports", url = "http://localhost:8082/api/v1/transports")
public interface TransportClientFeign {
    @PostMapping("/getByVinAndCompany")
    TransportResponse getByVinAndCompany(
            @RequestBody TransportRequest request,
            @RequestHeader("x-userId") String userId,
            @RequestHeader("x-roles") String roles
    );
}

package com.example.logist_sevice.config.feign;

import com.example.logist_sevice.web.dto.transport.TransportRequest;
import com.example.logist_sevice.web.dto.transport.TransportResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "transports", url = "http://localhost:8082/api/v1/transports")
public interface TransportFeignClient {
    @PostMapping("/getByVinAndCompany")
    TransportResponse getByVinAndCompany(
            @RequestBody TransportRequest request,
            @RequestHeader("x-userId") String userId,
            @RequestHeader("x-roles") String roles
    );
}

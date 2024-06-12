package com.example.dwh_service.config.feign;

import com.example.dwh_service.web.dto.CompanyAccessRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authorization", url = "http://localhost:8082/api/v1/authorization")
public interface AuthorizationFeignClient {

    @PostMapping("/dwhAccess")
    void canAccessDwh(
            @RequestBody CompanyAccessRequest request,
            @RequestHeader("x-userId") String userId,
            @RequestHeader("x-roles") String roles
    );
}

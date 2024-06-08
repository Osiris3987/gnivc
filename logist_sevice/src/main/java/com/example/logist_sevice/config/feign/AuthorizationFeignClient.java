package com.example.logist_sevice.config.feign;

import com.example.logist_sevice.web.dto.CompanyAccessRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "authorization", url = "http://localhost:8082/api/v1/authorization")
public interface AuthorizationFeignClient {

    @PostMapping("/companyAccess")
    void canAccessCompany(
            @RequestBody CompanyAccessRequest request,
            @RequestHeader("x-userId") String userId,
            @RequestHeader("x-roles") String roles
    );
}

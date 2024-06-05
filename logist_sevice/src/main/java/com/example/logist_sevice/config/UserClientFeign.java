package com.example.logist_sevice.config;

import com.example.logist_sevice.web.dto.CompanyDriverRequest;
import com.example.logist_sevice.web.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "users", url = "http://localhost:8082/api/v1/users")
public interface UserClientFeign {
    @PostMapping("/driver")
    UserDto getCompanyDriver(
            @RequestBody CompanyDriverRequest dto,
            @RequestHeader("x-userId") String userId,
            @RequestHeader("x-roles") String roles
    );
}

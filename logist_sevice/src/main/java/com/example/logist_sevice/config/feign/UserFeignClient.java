package com.example.logist_sevice.config.feign;

import com.example.logist_sevice.web.dto.user.CompanyDriverRequest;
import com.example.logist_sevice.web.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "users", url = "http://localhost:8082/api/v1/users")
public interface UserFeignClient {
    @PostMapping("/driver")
    UserDto getCompanyDriver(
            @RequestBody CompanyDriverRequest dto,
            @RequestHeader("x-userId") String userId,
            @RequestHeader("x-roles") String roles
    );
}

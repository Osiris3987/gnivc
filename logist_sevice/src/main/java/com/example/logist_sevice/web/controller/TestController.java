package com.example.logist_sevice.web.controller;

import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.logist_sevice.config.feign.TransportFeignClient;
import com.example.logist_sevice.config.feign.UserFeignClient;
import com.example.logist_sevice.web.dto.user.CompanyDriverRequest;
import com.example.logist_sevice.web.dto.transport.TransportRequest;
import com.example.logist_sevice.web.dto.transport.TransportResponse;
import com.example.logist_sevice.web.dto.user.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {
    private final UserContext userContext;

    private final UserFeignClient userFeignClient;

    private final TransportFeignClient transportFeignClient;

    private final ObjectMapper objectMapper;

    @PostMapping("/driver")
    @SneakyThrows
    public UserDto getCompanyDriver(@RequestBody CompanyDriverRequest dto) {
        return userFeignClient.getCompanyDriver(dto, userContext.getUserId().toString(), objectMapper.writeValueAsString(userContext.getRoles()));
    }

    @PostMapping("/transport")
    @SneakyThrows
    public TransportResponse getCom(@RequestBody TransportRequest dto) {
        return transportFeignClient.getByVinAndCompany(dto, userContext.getUserId().toString(), objectMapper.writeValueAsString(userContext.getRoles()));
    }


}

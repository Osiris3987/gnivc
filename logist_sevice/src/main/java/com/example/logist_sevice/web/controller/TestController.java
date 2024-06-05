package com.example.logist_sevice.web.controller;

import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.logist_sevice.config.TransportClientFeign;
import com.example.logist_sevice.config.UserClientFeign;
import com.example.logist_sevice.web.dto.CompanyDriverRequest;
import com.example.logist_sevice.web.dto.TransportRequest;
import com.example.logist_sevice.web.dto.TransportResponse;
import com.example.logist_sevice.web.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/test")
public class TestController {
    private final UserContext userContext;

    private final UserClientFeign userClientFeign;

    private final TransportClientFeign transportClientFeign;

    private final ObjectMapper objectMapper;

    @PostMapping("/driver")
    @SneakyThrows
    public UserDto getCompanyDriver(@RequestBody CompanyDriverRequest dto) {
        return userClientFeign.getCompanyDriver(dto, userContext.getUserId().toString(), objectMapper.writeValueAsString(userContext.getRoles()));
    }

    @PostMapping("/transport")
    @SneakyThrows
    public TransportResponse getCom(@RequestBody TransportRequest dto) {
        return transportClientFeign.getByVinAndCompany(dto, userContext.getUserId().toString(), objectMapper.writeValueAsString(userContext.getRoles()));
    }
}

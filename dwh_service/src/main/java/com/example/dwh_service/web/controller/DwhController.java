package com.example.dwh_service.web.controller;

import com.example.dwh_service.config.feign.AuthorizationFeignClient;
import com.example.dwh_service.model.DailyCompanyStatistics;
import com.example.dwh_service.service.AnalyticsService;
import com.example.dwh_service.web.dto.CompanyAccessRequest;
import com.example.gnivc_spring_boot_starter.UserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/stats")
@RequiredArgsConstructor
public class DwhController {

    private final AnalyticsService analyticsService;

    private final UserContext userContext;

    private final AuthorizationFeignClient authorizationClient;

    private final ObjectMapper objectMapper;

    @GetMapping("/{companyId}")
    @SneakyThrows
    public DailyCompanyStatistics getTasks(@PathVariable UUID companyId) {
        authorizationClient.canAccessDwh(
                new CompanyAccessRequest(companyId),
                userContext.getUserId().toString(),
                objectMapper.writeValueAsString(userContext.getRoles())
        );
        return analyticsService.findCompanyDailyStatistics(companyId.toString());
    }
}

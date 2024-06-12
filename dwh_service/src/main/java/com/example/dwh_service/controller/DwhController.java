package com.example.dwh_service.controller;

import com.example.dwh_service.model.DailyCompanyStatistics;
import com.example.dwh_service.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dwh")
@RequiredArgsConstructor
public class DwhController {
    private final AnalyticsService analyticsService;

    @GetMapping("/{companyId}")
    public DailyCompanyStatistics getTasks(@PathVariable UUID companyId) {
        return analyticsService.findCompanyDailyStatistics(companyId.toString());
    }
}

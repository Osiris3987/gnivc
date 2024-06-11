package com.example.dwh_service.controller;

import com.example.dwh_service.service.AnalyticsService;
import com.example.dwh_service.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dwh")
@RequiredArgsConstructor
public class TestController {
    private final AnalyticsService analyticsService;

    @GetMapping
    public List<Task> getTasks() {
        return analyticsService.findAll();
    }
}

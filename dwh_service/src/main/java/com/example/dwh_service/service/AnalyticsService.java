package com.example.dwh_service.service;

import com.example.dwh_service.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final ClickHouseJDBCDemo clickHouseJDBCDemo;

    @SneakyThrows
    public List<Task> findAll() {
        return clickHouseJDBCDemo.popularYearRoutes();
    }
}

package com.example.dwh_service.service;

import com.example.dwh_service.model.DailyCompanyStatistics;
import com.example.dwh_service.model.Task;
import com.example.dwh_service.repostiory.ClickHouseJDBC;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final ClickHouseJDBC clickHouseJDBC;

    @SneakyThrows
    public Integer findTasksByCompanyId(UUID companyId) {
        return clickHouseJDBC.recentTasks(companyId.toString());
    }

    @SneakyThrows
    public DailyCompanyStatistics findCompanyDailyStatistics(String companyId) {
        DailyCompanyStatistics dailyCompanyStatistics = new DailyCompanyStatistics();
        dailyCompanyStatistics.setCreatedTasks(clickHouseJDBC.recentTasks(companyId));
        dailyCompanyStatistics.setStartedRaces(clickHouseJDBC.recentRaceEvents("STARTED", companyId));
        dailyCompanyStatistics.setCompletedRaces(clickHouseJDBC.recentRaceEvents("COMPLETED", companyId));
        dailyCompanyStatistics.setCanceledRaces(clickHouseJDBC.recentRaceEvents("CANCELED", companyId));
        return dailyCompanyStatistics;
    }
}

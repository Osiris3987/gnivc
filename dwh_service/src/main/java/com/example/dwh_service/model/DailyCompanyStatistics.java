package com.example.dwh_service.model;

import lombok.Data;

@Data
public class DailyCompanyStatistics {
    private Integer createdTasks;

    private Integer startedRaces;

    private Integer completedRaces;

    private Integer canceledRaces;
}

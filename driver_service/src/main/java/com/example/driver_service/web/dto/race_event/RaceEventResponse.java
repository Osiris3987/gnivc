package com.example.driver_service.web.dto.race_event;

import com.example.driver_service.web.dto.RaceEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaceEventResponse {
    private RaceEventType eventType;
    private LocalDateTime createdAt;
}

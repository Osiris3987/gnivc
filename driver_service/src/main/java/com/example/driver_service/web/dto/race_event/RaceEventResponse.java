package com.example.logist_sevice.web.dto.race_event;

import com.example.logist_sevice.model.race.RaceEventType;
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

package com.example.logist_sevice.web.dto.race;

import com.example.logist_sevice.web.dto.race_event.RaceEventResponse;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class RaceResponse {

    private UUID id;

    private LocalDateTime createdAt;

    private LocalDateTime startedAt;

    private LocalDateTime endedAt;

    private UUID taskId;

    private RaceEventResponse latestEvent;
}

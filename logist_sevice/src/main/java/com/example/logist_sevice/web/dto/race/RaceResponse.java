package com.example.logist_sevice.web.dto.race;

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
}

package com.example.logist_sevice.model.race;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Table(name = "race_events")
@Embeddable
@Data
public class RaceEvent {
    @Column(name = "event_type")
    @Enumerated(value = EnumType.STRING)
    private RaceEventType eventType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

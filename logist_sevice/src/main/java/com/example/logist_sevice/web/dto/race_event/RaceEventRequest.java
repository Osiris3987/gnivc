package com.example.logist_sevice.web.dto.race_event;

import com.example.logist_sevice.model.race.RaceEventType;
import lombok.Data;


@Data
public class RaceEventRequest {

    private RaceEventType eventType;
    private String raceId;
}

package com.example.driver_service.web.dto.race_event;

import com.example.driver_service.web.dto.RaceEventType;
import lombok.Data;


@Data
public class RaceEventRequest {

    private RaceEventType eventType;

    private String raceId;

}

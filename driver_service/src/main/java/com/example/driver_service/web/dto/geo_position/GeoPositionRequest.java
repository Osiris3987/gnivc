package com.example.driver_service.web.dto.geo_position;

import lombok.Data;

@Data
public class GeoPositionRequest {
    private String point;

    private String raceId;
}

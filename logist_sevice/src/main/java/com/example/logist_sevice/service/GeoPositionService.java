package com.example.logist_sevice.service;

import com.example.logist_sevice.model.race.GeoPosition;
import com.example.logist_sevice.model.race.Race;
import com.example.logist_sevice.model.race.RaceEvent;
import com.example.logist_sevice.web.dto.geo_position.GeoPositionRequest;
import com.example.logist_sevice.web.dto.race_event.RaceEventRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GeoPositionService {
    private final RaceService raceService;

    @KafkaListener(topics = "race_geo_positions_topic", groupId = "logist_service_race_events")
    @SneakyThrows
    @Transactional
    public void addRaceEvent(String geoPosition) {
        Gson gson = new Gson();
        GeoPositionRequest geoPositionRequest = gson.fromJson(geoPosition, GeoPositionRequest.class);
        Race race = raceService.findById(UUID.fromString(geoPositionRequest.getRaceId()));
        GeoPosition geoPositionEntity = new GeoPosition();
        geoPositionEntity.setPoint(geoPositionRequest.getPoint());
        race.getGeoPositions().add(geoPositionEntity);
        raceService.update(race);
    }
}

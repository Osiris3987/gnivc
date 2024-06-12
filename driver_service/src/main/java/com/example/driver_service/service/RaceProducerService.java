package com.example.driver_service.service;

import com.example.driver_service.web.dto.geo_position.GeoPositionRequest;
import com.example.driver_service.web.dto.race.RaceRequest;
import com.example.driver_service.web.dto.race_event.RaceEventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaceProducerService {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendRace(RaceRequest request) {
        kafkaTemplate.send("race_topic", request);
    }

    public void sendRaceEvent(RaceEventRequest request) {
        kafkaTemplate.send("race_event_topic", request);
    }

    public void sendGeoPosition(GeoPositionRequest request) {
        kafkaTemplate.send("race_geo_positions_topic", request);
    }
}

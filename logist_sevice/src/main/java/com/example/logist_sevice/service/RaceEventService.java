package com.example.logist_sevice.service;

import com.example.logist_sevice.model.race.Race;
import com.example.logist_sevice.model.race.RaceEvent;
import com.example.logist_sevice.model.race.RaceEventType;
import com.example.logist_sevice.web.dto.RaceEventRequest;
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
public class RaceEventService {

    private final RaceService raceService;

    @KafkaListener(topics = "race_event_topic", groupId = "logist_service_race_events")
    @SneakyThrows
    @Transactional
    public void addRaceEvent(String raceEvent) {
        Gson gson = new Gson();
        RaceEventRequest raceEventRequest = gson.fromJson(raceEvent, RaceEventRequest.class);
        Race race = raceService.findById(UUID.fromString(raceEventRequest.getRaceId()));
        checkStartOrEndRaceEvent(raceEventRequest.getEventType(), race);
        RaceEvent raceEventEntity = new RaceEvent();
        raceEventEntity.setEventType(raceEventRequest.getEventType());
        raceEventEntity.setCreatedAt(LocalDateTime.now());
        race.getRaceEvents().add(raceEventEntity);
        raceService.update(race);
    }

    private void checkStartOrEndRaceEvent(RaceEventType eventType, Race race) {
        switch (eventType) {
            case STARTED -> race.setStartedAt(LocalDateTime.now());
            case COMPLETED -> race.setEndedAt(LocalDateTime.now());
        }
    }

}

package com.example.logist_sevice.service;

import com.example.logist_sevice.model.race.Race;
import com.example.logist_sevice.model.race.RaceEvent;
import com.example.logist_sevice.model.race.RaceEventType;
import com.example.logist_sevice.repository.RaceRepository;
import com.example.logist_sevice.web.dto.race_event.RaceEventRequest;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RaceEventService {

    private final RaceService raceService;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final RaceRepository raceRepository;

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

        raceRepository.createRaceEvent(
                raceEventEntity.getEventType().name(),
                raceEventEntity.getCreatedAt(),
                race.getId().toString(),
                raceEventRequest.getImage() == null ? null : raceEventRequest.getImage()
        );
    }

    private void checkStartOrEndRaceEvent(RaceEventType eventType, Race race) {
        switch (eventType) {
            case STARTED -> {
                raceRepository.setStartTime(race.getId(), LocalDateTime.now());
            }
            case COMPLETED -> {
                raceRepository.setEndTime(race.getId(), LocalDateTime.now());
            }
        }
    }

}

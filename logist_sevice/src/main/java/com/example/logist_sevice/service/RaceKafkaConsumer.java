package com.example.logist_sevice.service;

import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.logist_sevice.config.feign.AuthorizationFeignClient;
import com.example.logist_sevice.model.race.Race;
import com.example.logist_sevice.model.race.RaceEvent;
import com.example.logist_sevice.model.race.RaceEventType;
import com.example.logist_sevice.model.task.Task;
import com.example.logist_sevice.repository.RaceRepository;
import com.example.logist_sevice.web.dto.CompanyAccessRequest;
import com.example.logist_sevice.web.dto.race.RaceRequest;
import com.example.logist_sevice.web.mapper.RaceMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RaceKafkaConsumer {
    private final RaceRepository raceRepository;
    private final TaskService taskService;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @KafkaListener(topics = "race_topic", groupId = "logist_service_race_events")
    @Transactional
    public void createRaceFromProducer(String message) {
        Gson gson = new Gson();
        RaceRequest request = gson.fromJson(message, RaceRequest.class);
        Task task = taskService.findTaskById(request.getTaskId());
        Race race = new Race();
        race.setTask(task);
        race.setCreatedAt(LocalDateTime.now());
        race.setRaceEvents(List.of(assignRaceEventToNewRace()));
        race.setTask(task);
        raceRepository.save(race);
    }

    private RaceEvent assignRaceEventToNewRace() {
        RaceEvent raceEvent = new RaceEvent();
        raceEvent.setEventType(RaceEventType.CREATED);
        raceEvent.setCreatedAt(LocalDateTime.now());
        return raceEvent;
    }
}

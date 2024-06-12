package com.example.driver_service.web.controller;

import com.example.driver_service.config.feign.TaskFeignClient;
import com.example.driver_service.service.RaceProducerService;
import com.example.driver_service.web.dto.RaceAccessRequest;
import com.example.driver_service.web.dto.geo_position.GeoPositionRequest;
import com.example.driver_service.web.dto.race.RaceRequest;
import com.example.driver_service.web.dto.race_event.RaceEventRequest;
import com.example.gnivc_spring_boot_starter.UserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/races")
public class RaceController {

    private final ObjectMapper objectMapper;

    private final UserContext userContext;

    private final RaceProducerService raceProducerService;

    private final TaskFeignClient taskClient;

    @PostMapping("/events")
    @SneakyThrows
    public void sendRaceEvent(@RequestBody RaceEventRequest request) {
        taskClient.canAccessRace(
                new RaceAccessRequest(UUID.fromString(request.getRaceId())),
                userContext.getUserId().toString(),
                objectMapper.writeValueAsString(userContext.getRoles())
        );
        raceProducerService.sendRaceEvent(request);
    }

    @PostMapping("/geoPositions")
    @SneakyThrows
    public void sendGeoPosition(@RequestBody GeoPositionRequest request) {
        taskClient.canAccessRace(
                new RaceAccessRequest(UUID.fromString(request.getRaceId())),
                userContext.getUserId().toString(),
                objectMapper.writeValueAsString(userContext.getRoles())
        );
        raceProducerService.sendGeoPosition(request);
    }

    @PostMapping
    @SneakyThrows
    public void sendRace(@RequestBody RaceRequest request) {
        taskClient.canAccessTask(
                request,
                userContext.getUserId().toString(),
                objectMapper.writeValueAsString(userContext.getRoles())
        );
        raceProducerService.sendRace(request);
    }
}

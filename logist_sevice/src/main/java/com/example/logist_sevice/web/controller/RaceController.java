package com.example.logist_sevice.web.controller;

import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.logist_sevice.config.feign.AuthorizationFeignClient;
import com.example.logist_sevice.model.race.Race;
import com.example.logist_sevice.model.task.Task;
import com.example.logist_sevice.service.RaceService;
import com.example.logist_sevice.service.TaskService;
import com.example.logist_sevice.web.dto.CompanyAccessRequest;
import com.example.logist_sevice.web.dto.race.RaceRequest;
import com.example.logist_sevice.web.dto.race.RaceResponse;
import com.example.logist_sevice.web.mapper.RaceMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/races")
public class RaceController {
    private final RaceService raceService;
    private final TaskService taskService;
    private final RaceMapper raceMapper;
    private final ObjectMapper objectMapper;
    private final AuthorizationFeignClient authorizationClient;
    private final UserContext userContext;

    @PostMapping
    @SneakyThrows
    public RaceResponse createTask(@RequestBody RaceRequest request) {
        Task task = taskService.findTaskById(request.getTaskId());
        authorizationClient.canAccessCompany(
                new CompanyAccessRequest(UUID.fromString(task.getCompanyId())),
                userContext.getUserId().toString(),
                objectMapper.writeValueAsString(userContext.getRoles())
        );
        Race race = new Race();
        race.setTask(task);
        return raceMapper.toResponse(raceService.create(race));
    }

}

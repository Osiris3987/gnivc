package com.example.driver_service.web.controller;

import com.example.driver_service.config.feign.TaskFeignClient;
import com.example.driver_service.web.dto.race.RaceResponse;
import com.example.driver_service.web.dto.task.TaskResponse;
import com.example.gnivc_spring_boot_starter.UserContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final UserContext userContext;

    private final TaskFeignClient taskClient;

    private final ObjectMapper objectMapper;

    @GetMapping("/{companyId}")
    @SneakyThrows
    public List<TaskResponse> getAllTasksBtCompanyId(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "1") int limit,
            @PathVariable UUID companyId
    ) {
        return taskClient.getAllTasksBtCompanyId(
                offset,
                limit,
                companyId,
                userContext.getUserId().toString(),
                objectMapper.writeValueAsString(userContext.getRoles())
        );
    }

    @GetMapping("/{taskId}/races")
    @SneakyThrows
    public List<RaceResponse> getAllRacesByTaskId(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "1") int limit,
            @PathVariable UUID taskId
    ) {
        return taskClient.getAllRacesByTaskId(
                offset,
                limit,
                taskId,
                userContext.getUserId().toString(),
                objectMapper.writeValueAsString(userContext.getRoles())
        );
    }
}

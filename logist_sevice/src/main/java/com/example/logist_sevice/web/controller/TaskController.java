package com.example.logist_sevice.web.controller;

import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.logist_sevice.config.feign.AuthorizationFeignClient;
import com.example.logist_sevice.config.feign.TransportFeignClient;
import com.example.logist_sevice.config.feign.UserFeignClient;
import com.example.logist_sevice.model.task.Task;
import com.example.logist_sevice.service.RaceService;
import com.example.logist_sevice.service.TaskService;
import com.example.logist_sevice.web.dto.CompanyAccessRequest;
import com.example.logist_sevice.web.dto.race.RaceResponse;
import com.example.logist_sevice.web.dto.task.TaskRequest;
import com.example.logist_sevice.web.dto.task.TaskResponse;
import com.example.logist_sevice.web.dto.transport.TransportResponse;
import com.example.logist_sevice.web.dto.user.UserDto;
import com.example.logist_sevice.web.mapper.RaceMapper;
import com.example.logist_sevice.web.mapper.TaskMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    private final RaceService raceService;

    private final TaskMapper taskMapper;

    private final RaceMapper raceMapper;

    private final ObjectMapper objectMapper;

    private final UserFeignClient userClient;

    private final TransportFeignClient transportClient;

    private final AuthorizationFeignClient authorizationClient;

    private final UserContext userContext;

    @PostMapping
    @SneakyThrows
    public TaskResponse createTask(@RequestBody TaskRequest request) {
        UserDto userResponse = userClient.getCompanyDriver(
                request.getDriver(),
                userContext.getUserId().toString(),
                objectMapper.writeValueAsString(userContext.getRoles())
        );
        TransportResponse transportResponse = transportClient.getByVinAndCompany(
                request.getTransport(),
                userContext.getUserId().toString(),
                objectMapper.writeValueAsString(userContext.getRoles())
        );
        Task task = taskService.create(taskMapper.toEntity(request, userResponse, transportResponse));
        return taskMapper.toResponse(task);
    }

    @GetMapping("/{companyId}")
    @SneakyThrows
    public List<TaskResponse> getAllTasksByCompanyId(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "1") int limit,
            @PathVariable UUID companyId
            ){
        authorizationClient.canAccessCompany(
                new CompanyAccessRequest(companyId),
                userContext.getUserId().toString(),
                objectMapper.writeValueAsString(userContext.getRoles())
        );
        return taskMapper.toResponseList(taskService.findAllByCompanyId(offset, limit, companyId));
    }

    @GetMapping("/{taskId}/races")
    public List<RaceResponse> getAllRacesByTaskId(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "1") int limit,
            @PathVariable UUID taskId
    ) {
        Task task = taskService.findTaskById(taskId);
        return raceMapper.toResponseListWithLatestEvents(raceService.findAllByTask(task, offset, limit));
    }
}

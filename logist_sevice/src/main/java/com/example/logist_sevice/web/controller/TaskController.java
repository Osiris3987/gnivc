package com.example.logist_sevice.web.controller;

import com.example.gnivc_spring_boot_starter.UserContext;
import com.example.logist_sevice.config.TransportClientFeign;
import com.example.logist_sevice.config.UserClientFeign;
import com.example.logist_sevice.service.TaskService;
import com.example.logist_sevice.web.dto.TaskRequest;
import com.example.logist_sevice.web.dto.TransportResponse;
import com.example.logist_sevice.web.dto.UserDto;
import com.example.logist_sevice.web.mapper.TaskMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskMapper taskMapper;
    private final TaskService taskService;
    private final UserClientFeign userClient;
    private final TransportClientFeign transportClient;
    private final UserContext userContext;
    private final ObjectMapper objectMapper;
    @PostMapping
    @SneakyThrows
    public void createTask(@RequestBody TaskRequest request) {
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
        taskService.create(
                taskMapper.toEntity(request, userResponse, transportResponse)
        );
    }
}

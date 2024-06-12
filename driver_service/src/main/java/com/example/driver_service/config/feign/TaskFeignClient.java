package com.example.driver_service.config.feign;

import com.example.driver_service.web.dto.RaceAccessRequest;
import com.example.driver_service.web.dto.race.RaceRequest;
import com.example.driver_service.web.dto.race.RaceResponse;
import com.example.driver_service.web.dto.race_event.RaceEventRequest;
import com.example.driver_service.web.dto.task.TaskResponse;
import com.example.driver_service.web.dto.user.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "tasks", url = "http://localhost:8083/api/v1/tasks")
public interface TaskFeignClient {

    @GetMapping("/{companyId}")
    List<TaskResponse> getAllTasksBtCompanyId(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "1") int limit,
            @PathVariable UUID companyId,
            @RequestHeader("x-userId") String userId,
            @RequestHeader("x-roles") String roles
    );

    @GetMapping("/{taskId}/races")
    List<RaceResponse> getAllRacesByTaskId(
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "1") int limit,
            @PathVariable UUID taskId,
            @RequestHeader("x-userId") String userId,
            @RequestHeader("x-roles") String roles
    );

    @PostMapping("/raceAccess")
    void canAccessRace(
            @RequestBody RaceAccessRequest request,
            @RequestHeader("x-userId") String userId,
            @RequestHeader("x-roles") String roles
    );

    @PostMapping("/taskAccess")
    void canAccessTask(
            @RequestBody RaceRequest request,
            @RequestHeader("x-userId") String userId,
            @RequestHeader("x-roles") String roles
    );
}

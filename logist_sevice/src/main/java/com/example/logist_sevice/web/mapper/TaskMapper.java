package com.example.logist_sevice.web.mapper;

import com.example.logist_sevice.model.task.Task;
import com.example.logist_sevice.web.dto.TaskRequest;
import com.example.logist_sevice.web.dto.TaskResponse;
import com.example.logist_sevice.web.dto.TransportResponse;
import com.example.logist_sevice.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "taskRequest.startPoint", target = "startPoint")
    @Mapping(source = "taskRequest.endPoint", target = "endPoint")
    @Mapping(source = "taskRequest.description", target = "description")
    @Mapping(source = "userResponse.firstName", target = "driverFirstName")
    @Mapping(source = "userResponse.lastName", target = "driverLastName")
    @Mapping(source = "transportResponse.stateNumber", target = "transportStateNumber")
    @Mapping(ignore = true, source = "transportResponse.id", target = "id")
    @Mapping(source = "taskRequest.companyId", target = "companyId")
    Task toEntity(TaskRequest taskRequest, UserDto userResponse, TransportResponse transportResponse);

    @Mapping(source = "task.id", target = "id")
    TaskResponse toResponse(Task task);

    List<TaskResponse> toResponseList(List<Task> tasks);


}

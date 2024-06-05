package com.example.logist_sevice.web.mapper;

import com.example.logist_sevice.model.task.Task;
import com.example.logist_sevice.web.dto.TaskRequest;
import com.example.logist_sevice.web.dto.TransportResponse;
import com.example.logist_sevice.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "taskRequest.startPoint", target = "startPoint")
    @Mapping(source = "taskRequest.endPoint", target = "endPoint")
    @Mapping(source = "taskRequest.description", target = "description")
    @Mapping(source = "userResponse.firstName", target = "driverFirstName")
    @Mapping(source = "userResponse.lastName", target = "driverLastName")
    @Mapping(source = "transportResponse.stateNumber", target = "transportStateNumber")
    @Mapping(ignore = true, source = "transportResponse.id", target = "id")
    Task toEntity(TaskRequest taskRequest, UserDto userResponse, TransportResponse transportResponse);
}

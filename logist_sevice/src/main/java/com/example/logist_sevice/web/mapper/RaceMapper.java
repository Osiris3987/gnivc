package com.example.logist_sevice.web.mapper;

import com.example.logist_sevice.model.race.Race;
import com.example.logist_sevice.web.dto.race.RaceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RaceMapper {

    @Mapping(source = "race.id", target = "id")
    @Mapping(source = "race.task.id", target = "taskId")
    public abstract RaceResponse toResponse(Race race);

    public abstract List<RaceResponse> toResponseList(List<Race> races);
}

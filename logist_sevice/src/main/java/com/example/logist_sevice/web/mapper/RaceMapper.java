package com.example.logist_sevice.web.mapper;

import com.example.logist_sevice.model.race.Race;
import com.example.logist_sevice.model.race.RaceEvent;
import com.example.logist_sevice.web.dto.race.RaceResponse;
import com.example.logist_sevice.web.dto.race_event.RaceEventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class RaceMapper {

    @Mapping(source = "race.id", target = "id")
    @Mapping(source = "race.task.id", target = "taskId")
    public abstract RaceResponse toResponse(Race race);

    public abstract List<RaceResponse> toResponseList(List<Race> races);

    public List<RaceResponse> toResponseListWithLatestEvents(List<Race> races) {
        return races.stream().map(race -> {
            RaceEvent latestEvent = race.getRaceEvents().stream()
                    .max(Comparator.comparing(RaceEvent::getCreatedAt)).get();
            RaceResponse raceResponse = toResponse(race);
            raceResponse.setLatestEvent(new RaceEventResponse(latestEvent.getEventType(), latestEvent.getCreatedAt()));
            return raceResponse;
        }).toList();
    }
}

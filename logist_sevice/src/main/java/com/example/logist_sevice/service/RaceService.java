package com.example.logist_sevice.service;

import com.example.logist_sevice.model.exception.ResourceNotFoundException;
import com.example.logist_sevice.model.race.Race;
import com.example.logist_sevice.model.race.RaceEvent;
import com.example.logist_sevice.model.race.RaceEventType;
import com.example.logist_sevice.model.task.Task;
import com.example.logist_sevice.repository.RaceRepository;
import com.example.logist_sevice.web.dto.race.RaceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RaceService {
    private final RaceRepository raceRepository;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public Race create(Race race) {
        race.setCreatedAt(LocalDateTime.now());
        race.setRaceEvents(List.of(assignRaceEventToNewRace()));
        return raceRepository.save(race);
    }

    @Transactional(readOnly = true)
    public List<Race> findAllByTask(Task task, int offset, int limit) {
        return raceRepository.findRacesByTask(task, PageRequest.of(offset, limit));
    }

    @Transactional(readOnly = true)
    public Race findById(UUID id) {
        return raceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Race not found"));
    }

    @Transactional
    public Race update(Race race) {
        return raceRepository.save(race);
    }


    private RaceEvent assignRaceEventToNewRace() {
        RaceEvent raceEvent = new RaceEvent();
        raceEvent.setEventType(RaceEventType.CREATED);
        raceEvent.setCreatedAt(LocalDateTime.now());
        return raceEvent;
    }
}

package com.example.logist_sevice.service;

import com.example.logist_sevice.model.exception.ResourceNotFoundException;
import com.example.logist_sevice.model.race.Race;
import com.example.logist_sevice.model.task.Task;
import com.example.logist_sevice.repository.RaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RaceService {
    private final RaceRepository raceRepository;

    @Transactional
    public Race create(Race race) {
        race.setCreatedAt(LocalDateTime.now());
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
}

package com.example.logist_sevice.repository;


import com.example.logist_sevice.model.race.Race;
import com.example.logist_sevice.model.race.RaceEvent;
import com.example.logist_sevice.model.race.RaceEventType;
import com.example.logist_sevice.model.task.Task;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RaceRepository extends JpaRepository<Race, UUID> {
    List<Race> findRacesByTask(Task task, Pageable pageable);
    @Query(value = "SELECT re.*\n" +
            "FROM race_events re\n" +
            "JOIN (\n" +
            "    SELECT race_id, MAX(created_at) AS max_created_at\n" +
            "    FROM race_events\n" +
            "    WHERE race_id = :raceId\n" +
            "    GROUP BY race_id\n" +
            ") AS subquery ON re.race_id = subquery.race_id AND re.created_at = subquery.max_created_at",
            nativeQuery = true)
    RaceEvent findLatestRaceEvent(@Param("raceId") String raceId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO race_events (event_type, created_at, race_id) VALUES (:eventType, :createdAt, :raceId)", nativeQuery = true)
    void createRaceEvent(@Param("eventType") String eventType, @Param("createdAt") String createdAt, @Param("raceId") String raceId);

    @Modifying
    @Query("UPDATE Race r SET r.startedAt = :newStartTime WHERE r.id = :raceId")
    void setStartTime(@Param("raceId") UUID raceId, @Param("newStartTime") String newStartTime);

    // Метод для установки времени окончания гонки
    @Modifying
    @Query("UPDATE Race r SET r.endedAt = :newEndTime WHERE r.id = :raceId")
    void setEndTime(@Param("raceId") UUID raceId, @Param("newEndTime") String newEndTime);
}

package com.example.logist_sevice.model.race;

import com.example.logist_sevice.model.task.Task;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "races")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "started_at")
    private String startedAt;

    @Column(name = "ended_at")
    private String endedAt;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "race_events",
            joinColumns = @JoinColumn(
                    name = "race_id",
                    foreignKey = @ForeignKey(name = "fk_race_events_race")
            )
    )
    private List<RaceEvent> raceEvents;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "geo_positions",
            joinColumns = @JoinColumn(
                    name = "race_id",
                    foreignKey = @ForeignKey(name = "fk_race_events_geo_position")
            )
    )
    private List<GeoPosition> geoPositions;

    /**
     * create table races
     * (
     *     id varchar(36) primary key,
     *     created_at timestamp not null,
     *     started_at timestamp,
     *     ended_at timestamp,
     *     task_id varchar(36) not null,
     *     constraint fk_races_tasks foreign key (task_id) references tasks (id)
     * );
     */
}

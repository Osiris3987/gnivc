package com.example.logist_sevice.model.task;

import com.example.logist_sevice.model.race.Task;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

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

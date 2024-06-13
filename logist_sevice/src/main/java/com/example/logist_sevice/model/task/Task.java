package com.example.logist_sevice.model.task;

import com.example.logist_sevice.model.race.Race;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "start_point")
    private String startPoint;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "end_point")
    private String endPoint;

    @Column(name = "driver_first_name")
    private String driverFirstName;

    @Column(name = "driver_last_name")
    private String driverLastName;

    @Column(name = "driver_id")
    private String driverId;

    @Column(name = "description")
    private String description;

    @Column(name = "transport_state_number")
    private String transportStateNumber;

    @Column(name = "company_id")
    private String companyId;

    @OneToMany
    @JoinTable(name = "races_l",
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Race> races;

    /**
     * create table tasks
     * (
     *     id varchar(36) primary key,
     *     start_point varchar not null,
     *     end_point varchar not null,
     *     driver_first_name varchar not null,
     *     driver_second_name varchar not null,
     *     description varchar not null,
     *     transport_state_number varchar not null
     * );
     */
}

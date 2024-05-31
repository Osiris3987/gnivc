package com.example.portal_service.model.transport;

import com.example.portal_service.model.company.Company;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Data
@Table(name = "transports")
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    private String vin;

    private Integer year;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}

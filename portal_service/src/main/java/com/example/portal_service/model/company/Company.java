package com.example.portal_service.model.company;

import com.example.portal_service.model.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "companies")
@Data
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    private String inn;
    private String address;
    private String kpp;
    private String ogrn;
    private String owner;
    private String name;

    @ManyToMany
    @JoinTable(name = "companies_users",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @EqualsAndHashCode.Exclude
    private Set<User> users;

    /*
    id varchar(36) primary key,
    inn varchar not null,
    address varchar not null,
    kpp varchar not null,
    ogrn varchar not null,
    owner varchar not null
     */
}

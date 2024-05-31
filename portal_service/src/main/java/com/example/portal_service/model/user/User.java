package com.example.portal_service.model.user;

import com.example.portal_service.model.company.Company;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID id;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    private String username;

    private String email;

    @ManyToMany
    @JoinTable(name = "companies_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "company_id"))
    @EqualsAndHashCode.Exclude
    private Set<Company> companies;

    /*
    create table users
    (
    id varchar(36) primary key,
    name varchar not null,
    surname varchar not null,
    username varchar(255) not null,
    email varchar(255) not null
    );
     */
}

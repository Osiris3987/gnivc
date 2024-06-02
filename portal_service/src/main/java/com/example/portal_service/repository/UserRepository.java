package com.example.portal_service.repository;

import com.example.portal_service.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users (id, firstname, lastname, username, email) VALUES (:id, :name, :surname, :username, :email)", nativeQuery = true)
    void createUser(@Param("id") String id, @Param("name") String name, @Param("surname") String surname, @Param("username") String username, @Param("email") String email);

    @Query(value = "SELECT u.*\n" +
            "FROM users u\n" +
            "JOIN companies_users cu ON u.id = cu.user_id\n" +
            "WHERE cu.company_id = :companyId", nativeQuery = true)
    List<User> findAllUsersIdByCompanyId(@Param("companyId") String companyId);
}

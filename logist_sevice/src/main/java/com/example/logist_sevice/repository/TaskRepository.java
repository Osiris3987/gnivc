package com.example.logist_sevice.repository;

import com.example.logist_sevice.model.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}

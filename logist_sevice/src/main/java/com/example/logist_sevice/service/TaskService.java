package com.example.logist_sevice.service;

import com.example.logist_sevice.model.task.Task;
import com.example.logist_sevice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public Task create(Task task) {
        return taskRepository.save(task);
    }
}

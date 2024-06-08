package com.example.logist_sevice.service;

import com.example.logist_sevice.model.exception.ResourceNotFoundException;
import com.example.logist_sevice.model.task.Task;
import com.example.logist_sevice.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    @Transactional
    public Task create(Task task) {
        return taskRepository.save(task);
    }

    @Transactional(readOnly = true)
    public List<Task> findAllByCompanyId(int offset, int limit, UUID companyId) {
        return taskRepository.findTasksByCompanyId(companyId.toString(), PageRequest.of(offset, limit));
    }

    @Transactional(readOnly = true)
    public Task findTaskById(UUID id) {
        return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("task not found"));
    }
}
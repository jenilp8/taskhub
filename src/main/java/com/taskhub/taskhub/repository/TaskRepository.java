package com.taskhub.taskhub.repository;

import com.taskhub.taskhub.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByProjectId(Long projectId, Pageable pageable);

    Page<Task> findByProjectIdAndStatus(Long projectId, Task.Status status, Pageable pageable);

    Page<Task> findByProjectIdAndPriority(Long projectId, Task.Priority priority, Pageable pageable);

    Page<Task> findByProjectIdAndStatusAndPriority(Long projectId, Task.Status status, Task.Priority priority, Pageable pageable);
}
package com.taskhub.taskhub.repository;

import com.taskhub.taskhub.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Long> {

}

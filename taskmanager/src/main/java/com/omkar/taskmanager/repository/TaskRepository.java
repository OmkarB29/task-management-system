package com.omkar.taskmanager.repository;

import com.omkar.taskmanager.entity.Task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByTitleContainingIgnoreCase(String keyword);
    List<Task> findByCompleted(boolean completed);
    Page<Task> findByTitleContainingIgnoreCase(
            String keyword,
            Pageable pageable);
}



package com.omkar.taskmanager.repository;

import com.omkar.taskmanager.entity.Task;

import com.omkar.taskmanager.user.User;
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

    List<Task> findByUser(User user);
    Page<Task> findByUser(User user, Pageable pageable);

    List<Task> findByUserAndTitleContainingIgnoreCase(
            User user,
            String keyword
    );

    Page<Task> findByUserAndTitleContainingIgnoreCase(
            User user,
            String keyword,
            Pageable pageable
    );
    List<Task> findByUserAndCompleted(
            User user,
            boolean completed
    );
}



package com.omkar.taskmanager.repository;

import com.omkar.taskmanager.entity.Task;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface TaskRepository extends JpaRepository<Task,Long> {

}

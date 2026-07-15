package com.omkar.taskmanager.service;

import com.omkar.taskmanager.dto.TaskRequestDTO;
import com.omkar.taskmanager.dto.TaskResponseDTO;
import com.omkar.taskmanager.entity.Task;
import com.omkar.taskmanager.exception.AccessDeniedException;
import com.omkar.taskmanager.exception.TaskNotFoundException;
import com.omkar.taskmanager.mapper.TaskMapper;
import com.omkar.taskmanager.repository.TaskRepository;
import com.omkar.taskmanager.user.UserRepository;
import com.omkar.taskmanager.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private static final Logger logger =
            LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository,TaskMapper taskMapper,UserRepository userRepository){
        this.taskRepository=taskRepository;
        this.taskMapper=taskMapper;
        this.userRepository=userRepository;
    }

    public TaskResponseDTO saveTask(TaskRequestDTO dto){
        Task task = taskMapper.toEntity(dto);

        task.setUser(getCurrentUser());

        task.setCompleted(false);
        logger.info("Creating Task");
        Task saved=taskRepository.save(task);
        logger.info("Task Created with ID {}", saved.getId());

        return taskMapper.toDTO(saved);
    }

    public Page<TaskResponseDTO> getAllTasks(Pageable pageable){


        User currentUser = getCurrentUser();

        return taskRepository
                .findByUser(currentUser, pageable)
                .map(taskMapper::toDTO);

    }

    public TaskResponseDTO getTaskById(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id " + id));
        logger.info("Fetching Task {}", id);
        return taskMapper.toDTO(task);

    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO updatedTask){
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        if (!existingTask.getUser().getId().equals(getCurrentUser().getId())) {
            throw new AccessDeniedException("You are not allowed to access this task.");
        }

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.isCompleted());
        Task saved = taskRepository.save(existingTask);
        logger.info("Updating Task {}", id);
        return taskMapper.toDTO(saved);
    }

    public void deleteTask(Long id){
        logger.info("Deleting Task {}", id);
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found"));

        if (!existingTask.getUser().getId().equals(getCurrentUser().getId())) {
            throw new RuntimeException("Access Denied");
        }
        taskRepository.deleteById(id);
    }

    public List<TaskResponseDTO> searchTasks(String keyword){
        List<Task> tasks = taskRepository.findByUserAndTitleContainingIgnoreCase(
                getCurrentUser(),
                keyword
        );

        return tasks.stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public List<TaskResponseDTO> getTasksByStatus(boolean completed){
        return taskRepository.findByUserAndCompleted(
                        getCurrentUser(),
                        completed
                )
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public Page<TaskResponseDTO> searchTasksWithPaging(String keyword,Pageable pageable){
        return taskRepository.findByUserAndTitleContainingIgnoreCase(
                getCurrentUser(),
                keyword,
                pageable
        ).map(taskMapper::toDTO);
    }

    private User getCurrentUser() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

}

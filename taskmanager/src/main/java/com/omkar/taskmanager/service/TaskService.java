package com.omkar.taskmanager.service;

import com.omkar.taskmanager.dto.TaskRequestDTO;
import com.omkar.taskmanager.dto.TaskResponseDTO;
import com.omkar.taskmanager.entity.Task;
import com.omkar.taskmanager.exception.TaskNotFoundException;
import com.omkar.taskmanager.mapper.TaskMapper;
import com.omkar.taskmanager.repository.TaskRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private static final Logger logger =
            LoggerFactory.getLogger(TaskService.class);

    public TaskService(TaskRepository taskRepository,TaskMapper taskMapper){
        this.taskRepository=taskRepository;
        this.taskMapper=taskMapper;
    }

    public TaskResponseDTO saveTask(TaskRequestDTO dto){
        Task task = taskMapper.toEntity(dto);

        task.setCompleted(false);
        logger.info("Creating Task");
        Task saved=taskRepository.save(task);
        logger.info("Task Created with ID {}", saved.getId());

        return taskMapper.toDTO(saved);
    }

    public Page<TaskResponseDTO> getAllTasks(Pageable pageable){


        return taskRepository.findAll(pageable)
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
                        new TaskNotFoundException("Task not found with id " + id));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());

        Task saved = taskRepository.save(existingTask);
        logger.info("Updating Task {}", id);
        return taskMapper.toDTO(saved);
    }

    public void deleteTask(Long id){
        logger.info("Deleting Task {}", id);
        taskRepository.deleteById(id);
    }

    public List<TaskResponseDTO> searchTasks(String keyword){
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(keyword);

        return tasks.stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public List<TaskResponseDTO> getTasksByStatus(boolean completed){
        return taskRepository.findByCompleted(completed)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public Page<TaskResponseDTO> searchTasksWithPaging(String keyword,Pageable pageable){
        return taskRepository.findByTitleContainingIgnoreCase(keyword,pageable).map(taskMapper::toDTO);
    }

}

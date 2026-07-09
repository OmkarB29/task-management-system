package com.omkar.taskmanager.service;

import com.omkar.taskmanager.dto.TaskRequestDTO;
import com.omkar.taskmanager.dto.TaskResponseDTO;
import com.omkar.taskmanager.entity.Task;
import com.omkar.taskmanager.exception.TaskNotFoundException;
import com.omkar.taskmanager.repository.TaskRepository;


import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }

    public TaskResponseDTO saveTask(TaskRequestDTO dto){
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(false);

        Task saved=taskRepository.save(task);


        return TaskResponseDTO.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .completed(saved.isCompleted())
                .build();
    }

    public List<TaskResponseDTO> getAllTasks(){
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .map(task -> TaskResponseDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .completed(task.isCompleted())
                        .build())
                .toList();
    }

    public TaskResponseDTO getTaskById(Long id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id " + id));

        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .build();

    }

    public TaskResponseDTO updateTask(Long id, TaskRequestDTO updatedTask){
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id " + id));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());

        Task saved = taskRepository.save(existingTask);

        return TaskResponseDTO.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .completed(saved.isCompleted())
                .build();
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

}

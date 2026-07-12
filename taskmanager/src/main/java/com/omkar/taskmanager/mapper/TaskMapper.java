package com.omkar.taskmanager.mapper;

import com.omkar.taskmanager.dto.TaskRequestDTO;
import com.omkar.taskmanager.dto.TaskResponseDTO;
import com.omkar.taskmanager.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskResponseDTO toDTO(Task task){
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.isCompleted())
                .build();
    }

    public Task toEntity(TaskRequestDTO taskRequestDTO){
        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setCompleted(taskRequestDTO.isCompleted());
        return task;
    }

}

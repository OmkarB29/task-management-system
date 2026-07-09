package com.omkar.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TaskRequestDTO {
    //no id and isCompleted because client doesn't need this
    @NotBlank(message = "Title can not be empty")
    private String title;
    @NotBlank(message = "Description can not be empty")
    private String description;

    private boolean completed;
}

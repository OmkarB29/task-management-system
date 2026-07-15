package com.omkar.taskmanager.controller;

import com.omkar.taskmanager.dto.TaskRequestDTO;
import com.omkar.taskmanager.dto.TaskResponseDTO;
import com.omkar.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody @Valid TaskRequestDTO task){
        TaskResponseDTO savedTask= taskService.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @GetMapping
    public ResponseEntity<Page<TaskResponseDTO>> getAllTasks(Pageable pageable){

        return ResponseEntity.ok(taskService.getAllTasks(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id){
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id,
                                                      @RequestBody @Valid TaskRequestDTO task){
        return ResponseEntity.ok(taskService.updateTask(id,task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/search")
    public ResponseEntity<List<TaskResponseDTO>> searchTasks(@RequestParam String keyword){
        return ResponseEntity.ok(taskService.searchTasks(keyword));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TaskResponseDTO>> filterTasks(@RequestParam boolean completed){
        return ResponseEntity.ok(taskService.getTasksByStatus(completed));
    }

}

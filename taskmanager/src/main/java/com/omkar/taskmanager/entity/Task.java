package com.omkar.taskmanager.entity;
import com.omkar.taskmanager.user.User;
import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank(message ="Title can not be empty")
    private String title;
    @NotBlank(message = "Description can not be empty")
    private String description;

    private boolean completed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}

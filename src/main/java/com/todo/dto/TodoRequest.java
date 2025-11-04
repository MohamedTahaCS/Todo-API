package com.todo.dto;

import com.todo.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TodoRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private Priority priority;
}

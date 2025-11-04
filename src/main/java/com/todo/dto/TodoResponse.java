package com.todo.dto;

import com.todo.entity.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
}

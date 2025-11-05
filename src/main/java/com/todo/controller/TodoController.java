package com.todo.controller;

import com.todo.dto.TodoRequest;
import com.todo.dto.TodoResponse;
import com.todo.entity.Priority;
import com.todo.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/todos")
@Tag(name = "Todos", description = "Todo management APIs")
@SecurityRequirement(name = "bearerAuth")
@Slf4j
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    private String getUsername(Authentication authentication) {
        return ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    @PostMapping
    @Operation(summary = "Create a new todo", description = "Create a new todo item for the authenticated user")
    @ApiResponse(responseCode = "201", description = "Todo created successfully")
    public ResponseEntity<TodoResponse> createTodo(
            @Valid @RequestBody TodoRequest request,
            Authentication authentication
    ) {
        TodoResponse response = todoService.createTodo(getUsername(authentication), request);
        log.info("Todo created by {}: {}", getUsername(authentication), response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all todos", description = "Get all todo items for the authenticated user. Can be filtered by completed status and/or priority.")
    public ResponseEntity<Page<TodoResponse>> getAllTodos(
            @Parameter(description = "Filter by completion status (true/false)") @RequestParam(required = false) Boolean completed,
            @Parameter(description = "Filter by priority (LOW, MEDIUM, HIGH)") @RequestParam(required = false) Priority priority,
            @Parameter(description = "Page number (0-based)") @RequestParam(required = false, defaultValue = "0") Integer page,
            @Parameter(description = "Page size") @RequestParam(required = false, defaultValue = "10") Integer size,
            Authentication authentication
    ) {
        Page<TodoResponse> todos = todoService.getAllTodos(getUsername(authentication), completed, priority, page, size);
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get todo by ID", description = "Get a specific todo item by its ID")
    public ResponseEntity<TodoResponse> getTodoById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        TodoResponse todo = todoService.getTodoById(getUsername(authentication), id);
        log.debug("Todo fetched by {}: {}", getUsername(authentication), id);
        return ResponseEntity.ok(todo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update todo", description = "Update an existing todo item")
    public ResponseEntity<TodoResponse> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequest request,
            Authentication authentication
    ) {
        TodoResponse response = todoService.updateTodo(getUsername(authentication), id, request);
        log.info("Todo updated by {}: {}", getUsername(authentication), id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/toggle")
    @Operation(summary = "Toggle todo completion", description = "Toggle the completion status of a todo item")
    public ResponseEntity<TodoResponse> toggleTodoComplete(
            @PathVariable Long id,
            Authentication authentication
    ) {
        TodoResponse response = todoService.toggleTodoComplete(getUsername(authentication), id);
        log.info("Todo toggled by {}: {}", getUsername(authentication), id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete todo", description = "Delete a todo item by its ID")
    @ApiResponse(responseCode = "204", description = "Todo deleted successfully")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long id,
            Authentication authentication
    ) {
        todoService.deleteTodo(getUsername(authentication), id);
        log.info("Todo deleted by {}: {}", getUsername(authentication), id);
        return ResponseEntity.noContent().build();
    }
}

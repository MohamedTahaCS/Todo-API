package com.todo.service;

import com.todo.dto.TodoRequest;
import com.todo.dto.TodoResponse;
import com.todo.entity.Priority;
import com.todo.entity.Todo;
import com.todo.entity.User;
import com.todo.exception.TodoNotFoundException;
import com.todo.exception.UnauthorizedAccessException;
import com.todo.exception.UserNotFoundException;
import com.todo.repository.TodoRepository;
import com.todo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(TodoService.class);

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TodoResponse createTodo(String username, TodoRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Todo todo = Todo.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(false)
                .priority(request.getPriority() != null ? request.getPriority() : Priority.MEDIUM)
                .user(user)
                .build();

        todo = todoRepository.save(todo);

        return mapToResponse(todo);
    }

    public Page<TodoResponse> getAllTodos(String username, Boolean completed, Priority priority, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Page<Todo> page;
        if (completed != null && priority != null) {
            page = todoRepository.findByUserIdAndCompletedAndPriority(user.getId(), completed, priority, pageable);
        } else if (completed != null) {
            page = todoRepository.findByUserIdAndCompleted(user.getId(), completed, pageable);
        } else if (priority != null) {
            page = todoRepository.findByUserIdAndPriority(user.getId(), priority, pageable);
        } else {
            page = todoRepository.findByUserId(user.getId(), pageable);
        }
        Page<TodoResponse> mapped = page.map(this::mapToResponse);
        log.debug("Fetched todos page for {}: page={}, size={}, total={}", username, pageable.getPageNumber(), pageable.getPageSize(), mapped.getTotalElements());
        return mapped;
    }

    public TodoResponse getTodoById(String username, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        if (!todo.getUser().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("You are not allowed to access this todo");
        }

        return mapToResponse(todo);
    }

    @Transactional
    public TodoResponse updateTodo(String username, Long id, TodoRequest request) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        if (!todo.getUser().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("You are not allowed to access this todo");
        }

        todo.setTitle(request.getTitle());
        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }
        if (request.getPriority() != null) {
            todo.setPriority(request.getPriority());
        }

        todo = todoRepository.save(todo);

        return mapToResponse(todo);
    }

    @Transactional
    public TodoResponse toggleTodoComplete(String username, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        if (!todo.getUser().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("You are not allowed to access this todo");
        }

        todo.setCompleted(!todo.getCompleted());
        todo = todoRepository.save(todo);

        return mapToResponse(todo);
    }

    @Transactional
    public void deleteTodo(String username, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found"));

        if (!todo.getUser().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("You are not allowed to access this todo");
        }

        todoRepository.delete(todo);
    }

    private TodoResponse mapToResponse(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .completed(todo.getCompleted())
                .priority(todo.getPriority())
                .createdAt(todo.getCreatedAt())
                .updatedAt(todo.getUpdatedAt())
                .userId(todo.getUser().getId())
                .build();
    }
}

package com.todo.service;

import com.todo.dto.TodoRequest;
import com.todo.dto.TodoResponse;
import com.todo.entity.Priority;
import org.springframework.data.domain.Page;

public interface TodoService {
    TodoResponse createTodo(String username, TodoRequest request);
    Page<TodoResponse> getAllTodos(String username, Boolean completed, Priority priority, Integer page, Integer size);
    TodoResponse getTodoById(String username, Long id);
    TodoResponse updateTodo(String username, Long id, TodoRequest request);
    TodoResponse toggleTodoComplete(String username, Long id);
    void deleteTodo(String username, Long id);
}

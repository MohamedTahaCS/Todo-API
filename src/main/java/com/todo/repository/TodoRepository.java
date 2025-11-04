package com.todo.repository;

import com.todo.entity.Priority;
import com.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Page<Todo> findByUserId(Long userId, Pageable pageable);
    Page<Todo> findByUserIdAndCompleted(Long userId, Boolean completed, Pageable pageable);
    Page<Todo> findByUserIdAndPriority(Long userId, Priority priority, Pageable pageable);
    Page<Todo> findByUserIdAndCompletedAndPriority(Long userId, Boolean completed, Priority priority, Pageable pageable);
}

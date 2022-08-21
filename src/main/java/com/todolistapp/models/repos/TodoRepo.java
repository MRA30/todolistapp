package com.todolistapp.models.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todolistapp.models.entity.Todo;

public interface TodoRepo extends JpaRepository<Todo, Long>{
    
}

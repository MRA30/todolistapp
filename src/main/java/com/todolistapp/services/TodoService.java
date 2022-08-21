package com.todolistapp.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolistapp.models.entity.Todo;
import com.todolistapp.models.repos.TodoRepo;

@Service
@Transactional
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    public Todo save(Todo todo){
        return todoRepo.save(todo);
    }

    public Todo findOne(Long id){
        Optional<Todo> todo = todoRepo.findById(id);
        if(todo == null){
            return null;
        }
        return todo.get();
    }

    public Iterable<Todo> findAll(){
        return todoRepo.findAll();
    }

    public void removeOne(Long id){
        todoRepo.deleteById(id);
    }
    
}

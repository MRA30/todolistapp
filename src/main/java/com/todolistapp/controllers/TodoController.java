package com.todolistapp.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolistapp.dto.ResponseData;
import com.todolistapp.dto.TodoData;
import com.todolistapp.models.entity.Todo;
import com.todolistapp.services.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Todo>> create(@Valid @RequestBody TodoData todoData, Errors errors){
        ResponseData<Todo> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Todo todo = modelMapper.map(todoData, Todo.class);

        responseData.setStatus(true);
        responseData.setPayload(todoService.save(todo));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/{id}")
    public Todo findOne(@PathVariable("id") Long id){
        return todoService.findOne(id);
    }

    @GetMapping
    public Iterable<Todo> findAll(){
        return todoService.findAll();
    }

    @DeleteMapping("/{id}")
    public void removeOne(@PathVariable("id") Long id){
        todoService.removeOne(id);
    }

    @PatchMapping("/{id}/{todo}")
    public ResponseEntity<Todo> updatepath(@PathVariable("id") Long id, @PathVariable String todo){
        try {
            Todo todos = todoService.findOne(id);
            todos.setTodo(todo);
            return new ResponseEntity<Todo>(todoService.save(todos), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
}

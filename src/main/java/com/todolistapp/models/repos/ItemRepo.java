package com.todolistapp.models.repos;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.todolistapp.models.entity.Item;

public interface ItemRepo extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.todo.id = :todoId")
    public List<Item> findItemByTodo(@PathParam("todoId") Long todoId);
    
}

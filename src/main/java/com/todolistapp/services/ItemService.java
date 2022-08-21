package com.todolistapp.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolistapp.models.entity.Item;
import com.todolistapp.models.repos.ItemRepo;

@Service
@Transactional
public class ItemService {
    
    @Autowired
    private ItemRepo itemRepo;

    public Item save(Item item){
        return itemRepo.save(item);
    }

    public Item findOne(Long id){
        Optional<Item> item = itemRepo.findById(id);
        if(item == null){
            return null;
        }
        return item.get();
    }

    public Iterable<Item> findAll(){
        return itemRepo.findAll();
    }

    public void removeOne(Long id){
        itemRepo.deleteById(id);
    }

    public List<Item> findByTodo(Long todoId){
        return itemRepo.findItemByTodo(todoId);
    }

}

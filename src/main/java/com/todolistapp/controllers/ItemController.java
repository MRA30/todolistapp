package com.todolistapp.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todolistapp.dto.ItemData;
import com.todolistapp.dto.ResponseData;
import com.todolistapp.models.entity.Item;
import com.todolistapp.models.repos.ItemRepo;
import com.todolistapp.services.ItemService;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<ResponseData<Item>> create(@Valid @RequestBody ItemData itemData, Errors errors){
        ResponseData<Item> responseData = new ResponseData<>();
        if (errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        Item item = modelMapper.map(itemData, Item.class);

        responseData.setStatus(true);
        responseData.setPayload(itemService.save(item));
        return ResponseEntity.ok(responseData);
    }

    @GetMapping
    public Iterable<Item> findAll(){
        return itemService.findAll();
    }

    @GetMapping("/{id}")
    public Item findById(@PathVariable("id") Long id){
        return itemService.findOne(id);
    }
    
    @DeleteMapping("/{id}")
        public void removeOne(@PathVariable("id") Long id){
            itemService.removeOne(id);
        }
    
   @PutMapping("/{id}")
    public ResponseEntity<ResponseData<Item>> update(@Valid @RequestBody ItemData itemData, @PathVariable("id") Long id, Errors errors){
        ResponseData<Item> responseData = new ResponseData<>();
        if (errors.hasErrors()){
            for (ObjectError error : errors.getAllErrors()) {
                responseData.getMessages().add(error.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }

        Item item = modelMapper.map(itemData, Item.class);

        return itemRepo.findById(id).map(
            i -> {
                i.setItem(item.getItem());
                i.setCek(item.isCek());
                
                responseData.setStatus(true);
                responseData.setPayload(itemRepo.save(i));
                return ResponseEntity.ok(responseData);
            }).orElseGet(() ->{
                responseData.setStatus(true);
                responseData.setPayload(itemRepo.save(item));
                return ResponseEntity.ok(responseData);
        });
    }

    @PatchMapping("/{id}/{cek}")
    public ResponseEntity<Item> updatepath(@PathVariable("id") Long id, @PathVariable boolean cek){
        try {
            Item item = itemService.findOne(id);
            item.setCek(cek);
            return new ResponseEntity<Item>(itemService.save(item), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/filter/todo/{todoId}")
    public List<Item> getItemByTodo(@PathVariable("todoId") Long todoId){
        return itemService.findByTodo(todoId);
    }
}

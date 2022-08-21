package com.todolistapp.dto;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.todolistapp.models.entity.Todo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString

public class ItemData {

    @NotEmpty(message = "item is required")
    private String item;

    private boolean cek;
    
    @ManyToOne
    private Todo todo;
    
}

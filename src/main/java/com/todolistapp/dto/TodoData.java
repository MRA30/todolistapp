package com.todolistapp.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
public class TodoData {
    
    @NotEmpty(message = "todo is required")
    private String todo;

}

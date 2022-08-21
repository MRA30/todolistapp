package com.todolistapp.dto;

import java.util.List;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Setter
@Getter
@ToString
public class ResponseData<T> {

    private boolean status;
    private List<String> messages;
    private T payload;
}

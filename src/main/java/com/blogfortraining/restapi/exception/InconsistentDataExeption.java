package com.blogfortraining.restapi.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InconsistentDataExeption extends RuntimeException{
    
    private HttpStatus status;
    private String message;

    public InconsistentDataExeption(HttpStatus status, String message){
        this.message = message;
        this.status = status;
    }

    public InconsistentDataExeption (HttpStatus status,String message, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }
}

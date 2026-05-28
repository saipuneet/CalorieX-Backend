package com.CalorieX.CalorieX_Backend.exception;

public class EmailIsFoundException extends RuntimeException{

    public EmailIsFoundException(String message){
        super(message);
    }
}

package com.CalorieX.CalorieX_Backend.exception;

public class PasswordIsIncorrectException extends RuntimeException{

    public PasswordIsIncorrectException(String message){
        super(message);
    }
}

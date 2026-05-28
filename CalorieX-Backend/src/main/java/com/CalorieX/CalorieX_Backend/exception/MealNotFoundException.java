package com.CalorieX.CalorieX_Backend.exception;

public class MealNotFoundException extends RuntimeException{

    public MealNotFoundException(String message){

        super(message);
    }
}

package com.CalorieX.CalorieX_Backend.exception;

import com.CalorieX.CalorieX_Backend.entity.Goal;

public class GoalNotFoundException extends RuntimeException{

    public GoalNotFoundException(String message){
        super(message);
    }
}

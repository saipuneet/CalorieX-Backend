package com.CalorieX.CalorieX_Backend.dto;

import com.CalorieX.CalorieX_Backend.entity.User;
import jakarta.persistence.OneToOne;

public class SetGoalsRequest {

    private Integer targetCalories;

    private Double targetProtein;

    private Double targetFats;

    private Double targetCarbs;



    public SetGoalsRequest(){

    }

    public Integer getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(Integer targetCalories) {
        this.targetCalories = targetCalories;
    }

    public Double getTargetProtein() {
        return targetProtein;
    }

    public void setTargetProtein(Double targetProtein) {
        this.targetProtein = targetProtein;
    }

    public Double getTargetFats() {
        return targetFats;
    }

    public void setTargetFats(Double targetFats) {
        this.targetFats = targetFats;
    }

    public Double getTargetCarbs() {
        return targetCarbs;
    }

    public void setTargetCarbs(Double targetCarbs) {
        this.targetCarbs = targetCarbs;
    }



}

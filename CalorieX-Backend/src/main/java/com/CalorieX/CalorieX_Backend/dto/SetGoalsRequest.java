package com.CalorieX.CalorieX_Backend.dto;

import com.CalorieX.CalorieX_Backend.entity.User;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SetGoalsRequest {

    @NotNull
    @Min(0)
    private Integer targetCalories;
    @NotNull
    @Min(0)
    private Double targetProtein;
    @NotNull
    @Min(0)
    private Double targetFats;
    @NotNull
    @Min(0)
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

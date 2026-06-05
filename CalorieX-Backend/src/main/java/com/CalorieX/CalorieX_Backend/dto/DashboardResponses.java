package com.CalorieX.CalorieX_Backend.dto;

public class DashboardResponses {


    private Integer totalCalories;
    private Integer goalCalories;
    private Integer remainingCalories;
    private Double currentWeight;
    private Double weightChange;
    private Integer mealsLoggedToday;

    public DashboardResponses(){

    }

    public Integer getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(Integer totalCalories) {
        this.totalCalories = totalCalories;
    }

    public Integer getGoalCalories() {
        return goalCalories;
    }

    public void setGoalCalories(Integer goalCalories) {
        this.goalCalories = goalCalories;
    }

    public Integer getRemainingCalories() {
        return remainingCalories;
    }

    public void setRemainingCalories(Integer remainingCalories) {
        this.remainingCalories = remainingCalories;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Double getWeightChange() {
        return weightChange;
    }

    public void setWeightChange(Double weightChange) {
        this.weightChange = weightChange;
    }

    public Integer getMealsLoggedToday() {
        return mealsLoggedToday;
    }

    public void setMealsLoggedToday(Integer mealsLoggedToday) {
        this.mealsLoggedToday = mealsLoggedToday;
    }
}

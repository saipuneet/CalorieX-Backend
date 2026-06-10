package com.CalorieX.CalorieX_Backend.dto;

public class DashboardResponses {


    private Integer totalCalories;
    private Integer goalCalories;
    private Integer remainingCalories;
    private Double currentWeight;
    private Double weightChange;
    private Integer mealsLoggedToday;
    private Double proteinConsumed;
    private Double proteinGoal;

    private Double carbsConsumed;
    private Double carbsGoal;

    private Double fatsConsumed;
    private Double fatsGoal;

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

    public Double getProteinConsumed() {
        return proteinConsumed;
    }

    public void setProteinConsumed(Double proteinConsumed) {
        this.proteinConsumed = proteinConsumed;
    }

    public Double getProteinGoal() {
        return proteinGoal;
    }

    public void setProteinGoal(Double proteinGoal) {
        this.proteinGoal = proteinGoal;
    }

    public Double getCarbsConsumed() {
        return carbsConsumed;
    }

    public void setCarbsConsumed(Double carbsConsumed) {
        this.carbsConsumed = carbsConsumed;
    }

    public Double getCarbsGoal() {
        return carbsGoal;
    }

    public void setCarbsGoal(Double carbsGoal) {
        this.carbsGoal = carbsGoal;
    }

    public Double getFatsConsumed() {
        return fatsConsumed;
    }

    public void setFatsConsumed(Double fatsConsumed) {
        this.fatsConsumed = fatsConsumed;
    }

    public Double getFatsGoal() {
        return fatsGoal;
    }

    public void setFatsGoal(Double fatsGoal) {
        this.fatsGoal = fatsGoal;
    }
}

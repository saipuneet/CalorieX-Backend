package com.CalorieX.CalorieX_Backend.dto;

public class GoalProgressResponse {

    private Integer targetCalories;

    private Integer consumedCalories;

    private Integer remainingCalories;

    private Double progressPercentage;

    public GoalProgressResponse(){

    }

    public Integer getTargetCalories() {
        return targetCalories;
    }

    public void setTargetCalories(Integer targetCalories) {
        this.targetCalories = targetCalories;
    }

    public Integer getConsumedCalories() {
        return consumedCalories;
    }

    public void setConsumedCalories(Integer consumedCalories) {
        this.consumedCalories = consumedCalories;
    }

    public Integer getRemainingCalories() {
        return remainingCalories;
    }

    public void setRemainingCalories(Integer remainingCalories) {
        this.remainingCalories = remainingCalories;
    }

    public Double getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(Double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}

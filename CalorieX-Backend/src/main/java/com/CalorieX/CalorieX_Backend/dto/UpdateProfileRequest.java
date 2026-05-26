package com.CalorieX.CalorieX_Backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UpdateProfileRequest {

    @NotNull(message = "Age should not be negative or zero")
    private Integer age;

    @NotNull(message = "this field is required and should not be negative")
    private Double height;

    @NotNull(message = "This field is required and should not be negative")
    private Double weight;

    @NotBlank(message = "Goal is required")
    private String goal;

    @NotBlank(message = "activityLevel is required")
    private String activityLevel;

    public UpdateProfileRequest(){

    }

    public UpdateProfileRequest(Integer age, Double height, Double weight, String goal, String activityLevel) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.goal = goal;
        this.activityLevel = activityLevel;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        this.activityLevel = activityLevel;
    }




}

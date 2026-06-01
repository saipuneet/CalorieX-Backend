package com.CalorieX.CalorieX_Backend.dto;

import com.CalorieX.CalorieX_Backend.entity.MealType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddMealRequest {

    @NotBlank
    private String mealName;

    @NotNull
    @Min(0)
    private Integer calories;

    @NotNull
    @Min(0)
    private Double protein;

    @NotNull
    @Min(0)
    private Double carbs;

    @NotNull
    @Min(0)
    private Double fats;

    @NotNull
    private MealType mealType;

    public AddMealRequest(String mealName, Integer calories, Double protein, Double carbs, Double fats, MealType mealType) {
        this.mealName = mealName;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.mealType = mealType;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
}

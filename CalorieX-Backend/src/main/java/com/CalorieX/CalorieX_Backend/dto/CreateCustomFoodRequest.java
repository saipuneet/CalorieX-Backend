package com.CalorieX.CalorieX_Backend.dto;

import com.CalorieX.CalorieX_Backend.entity.MealType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CreateCustomFoodRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer calories;

    @NotNull
    @Positive
    private Double protein;

    @NotNull
    @Positive
    private Double carbs;

    @NotNull
    @Positive
    private Double fats;

    private Boolean addTOToday = false;

    private Double Quantity;

    private MealType mealType;

    public CreateCustomFoodRequest(){

    }

    public CreateCustomFoodRequest(String name, Integer calories, Double protein, Double carbs, Double fats, Boolean addTOToday, Double quantity, MealType mealType) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.addTOToday = addTOToday;
        Quantity = quantity;
        this.mealType = mealType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getAddTOToday() {
        return addTOToday;
    }

    public void setAddTOToday(Boolean addTOToday) {
        this.addTOToday = addTOToday;
    }

    public Double getQuantity() {
        return Quantity;
    }

    public void setQuantity(Double quantity) {
        Quantity = quantity;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
}

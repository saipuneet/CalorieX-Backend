package com.CalorieX.CalorieX_Backend.dto;

import com.CalorieX.CalorieX_Backend.entity.MealType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddFoodToMealRequest {

    @NotNull(message = "FoodId is Required")
    private Long foodId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount is greater than 0")
    private Double amount;

    private String unit;

    @NotNull(message = "Meal type is required")
    private MealType mealType;

    public AddFoodToMealRequest(){

    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

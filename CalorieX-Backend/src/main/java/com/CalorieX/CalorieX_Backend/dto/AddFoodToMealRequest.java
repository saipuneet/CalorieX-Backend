package com.CalorieX.CalorieX_Backend.dto;

import com.CalorieX.CalorieX_Backend.entity.MealType;

public class AddFoodToMealRequest {

    private Long foodId;

    private Double amount;

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
}

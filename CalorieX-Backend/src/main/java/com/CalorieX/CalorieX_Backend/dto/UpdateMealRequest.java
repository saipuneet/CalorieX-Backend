package com.CalorieX.CalorieX_Backend.dto;

import com.CalorieX.CalorieX_Backend.entity.MealType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class UpdateMealRequest {



     @NotNull
    private MealType mealType;

     @NotNull
     @Positive
     private Double quantity;










    public UpdateMealRequest(){

    }



    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }






    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }


}

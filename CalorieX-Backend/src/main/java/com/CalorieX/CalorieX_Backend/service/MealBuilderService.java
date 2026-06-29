package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.MealServiceResponse;
import com.CalorieX.CalorieX_Backend.entity.FoodSource;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.MealType;
import com.CalorieX.CalorieX_Backend.entity.User;

public interface MealBuilderService {

    Meal createMeal(
            User user,
            String mealName,
            Integer calories,
            Double protein,
            Double carbs,
            Double fats,
            Long foodId,
            FoodSource foodSource,
            Double quantity,
            String unit,
            MealType mealType
    );

    MealServiceResponse buildMealResponse(Meal meal);

}

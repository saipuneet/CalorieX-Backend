package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.AddFoodToMealRequest;
import com.CalorieX.CalorieX_Backend.dto.MealServiceResponse;

public interface MealsService {

    MealServiceResponse addFoodToMeal(AddFoodToMealRequest addFoodToMealRequest);
}

package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.AddFoodToMealRequest;
import com.CalorieX.CalorieX_Backend.dto.MealServiceResponse;
import com.CalorieX.CalorieX_Backend.entity.Meal;

import java.util.List;

public interface MealsService {

    MealServiceResponse addFoodToMeal(AddFoodToMealRequest addFoodToMealRequest);

    List<MealServiceResponse> getRecentMeals();
}

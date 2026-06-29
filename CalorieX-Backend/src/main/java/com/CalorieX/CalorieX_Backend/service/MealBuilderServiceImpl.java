package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.MealServiceResponse;
import com.CalorieX.CalorieX_Backend.entity.FoodSource;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.MealType;
import com.CalorieX.CalorieX_Backend.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MealBuilderServiceImpl implements MealBuilderService{

    // this method is a helper method where we will be using in addfoodtomeal method for create meal object
    @Override
    public Meal createMeal(
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
            MealType mealType) {

        Meal meal = new Meal();

        meal.setMealName(mealName);
        meal.setCalories(calories);
        meal.setProtein(protein);
        meal.setCarbs(carbs);
        meal.setFats(fats);

        meal.setFoodId(foodId);
        meal.setFoodSource(foodSource);
        meal.setQuantity(quantity);
        meal.setUnit(unit);

        meal.setMealType(mealType);
        meal.setDate(LocalDate.now());
        meal.setUser(user);

        return meal;
    }


    // this is a helper method and we will using method in addfoodtomeal and this method is used for converting the meal object into dto (data transfer object)

    @Override
    public MealServiceResponse buildMealResponse(Meal meal) {

        MealServiceResponse response = new MealServiceResponse();

        response.setId(meal.getId());
        response.setMealName(meal.getMealName());
        response.setCalories(meal.getCalories());
        response.setProtein(meal.getProtein());
        response.setCarbs(meal.getCarbs());
        response.setFats(meal.getFats());
        response.setMealType(meal.getMealType());
        response.setDate(meal.getDate());
        response.setFoodId(meal.getFoodId());
        response.setQuantity(meal.getQuantity());
        response.setUnit(meal.getUnit());

        return response;
    }

}

package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.AddFoodToMealRequest;
import com.CalorieX.CalorieX_Backend.dto.FoodDetailsResponse;
import com.CalorieX.CalorieX_Backend.dto.MealServiceResponse;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.exception.UserNotFoundException;
import com.CalorieX.CalorieX_Backend.repository.MealRepository;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MealServiceImpl implements MealsService{

    /*
    This mealservice class is using with third party api called spoonacular"
     */

    private final MealRepository mealRepository;

    private final UserRepository userRepository;

    private final FoodService foodService;

    public MealServiceImpl(MealRepository mealRepository,UserRepository userRepository,FoodService foodService){
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.foodService = foodService;
    }

    @Override
    public MealServiceResponse addFoodToMeal(AddFoodToMealRequest addFoodToMealRequest) {

        // Get logged-in user's email from JWT
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // Find user in database
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        // Get nutrition details from Spoonacular
        FoodDetailsResponse foodDetailsResponse =
                foodService.getFoodDetails(
                        addFoodToMealRequest.getFoodId(),
                        addFoodToMealRequest.getAmount());

        // Create Meal entity
        Meal meal = new Meal();

        meal.setMealName(foodDetailsResponse.getName());
        meal.setCalories(foodDetailsResponse.getCalories().intValue());
        meal.setProtein(foodDetailsResponse.getProtein());
        meal.setCarbs(foodDetailsResponse.getCarbs());
        meal.setFats(foodDetailsResponse.getFat());
        meal.setMealType(addFoodToMealRequest.getMealType());
        meal.setDate(LocalDate.now());
        meal.setUser(user);

        // Save meal
        Meal savedMeal = mealRepository.save(meal);

        // Convert Entity -> DTO
        MealServiceResponse response = new MealServiceResponse();

        response.setId(savedMeal.getId());
        response.setMealName(savedMeal.getMealName());
        response.setCalories(savedMeal.getCalories());
        response.setProtein(savedMeal.getProtein());
        response.setCarbs(savedMeal.getCarbs());
        response.setFats(savedMeal.getFats());
        response.setMealType(savedMeal.getMealType());
        response.setDate(savedMeal.getDate());

        return response;
    }

}

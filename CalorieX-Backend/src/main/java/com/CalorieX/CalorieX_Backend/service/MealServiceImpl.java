package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.AddFoodToMealRequest;
import com.CalorieX.CalorieX_Backend.dto.FoodDetailsResponse;
import com.CalorieX.CalorieX_Backend.dto.MealServiceResponse;
import com.CalorieX.CalorieX_Backend.entity.Food;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.exception.UserNotFoundException;
import com.CalorieX.CalorieX_Backend.repository.FoodRepository;
import com.CalorieX.CalorieX_Backend.repository.MealRepository;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MealServiceImpl implements MealsService {

    /*
    This mealservice class is using with third party api called spoonacular"
     */

    private final MealRepository mealRepository;

    private final UserRepository userRepository;

    private final FoodService foodService;

    private final FoodRepository foodRepository;

    public MealServiceImpl(MealRepository mealRepository, UserRepository userRepository, FoodService foodService, FoodRepository foodRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.foodService = foodService;
        this.foodRepository = foodRepository;
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

        Meal meal = new Meal();

        // First check local database
        Food food = foodRepository
                .findById(addFoodToMealRequest.getFoodId())
                .orElse(null);

        if (food != null) {

            Double amount = addFoodToMealRequest.getAmount();

            // Nutrition calculation based on 100g
            double calories =
                    (food.getCalories() * amount) / 100;

            double protein =
                    (food.getProtein() * amount) / 100;

            double carbs =
                    (food.getCarbs() * amount) / 100;

            double fats =
                    (food.getFats() * amount) / 100;

            meal.setMealName(food.getName());
            meal.setCalories((int) calories);
            meal.setProtein(protein);
            meal.setCarbs(carbs);
            meal.setFats(fats);

        } else {

            // Fallback to Spoonacular
            FoodDetailsResponse foodDetailsResponse =
                    foodService.getFoodDetails(
                            addFoodToMealRequest.getFoodId(),
                            addFoodToMealRequest.getAmount());

            meal.setMealName(foodDetailsResponse.getName());
            meal.setCalories(foodDetailsResponse.getCalories().intValue());
            meal.setProtein(foodDetailsResponse.getProtein());
            meal.setCarbs(foodDetailsResponse.getCarbs());
            meal.setFats(foodDetailsResponse.getFat());
        }

        // Common meal fields
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
package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.AddFoodToMealRequest;
import com.CalorieX.CalorieX_Backend.dto.FoodDetailsResponse;
import com.CalorieX.CalorieX_Backend.dto.MealServiceResponse;
import com.CalorieX.CalorieX_Backend.entity.*;
import com.CalorieX.CalorieX_Backend.exception.UserNotFoundException;
import com.CalorieX.CalorieX_Backend.repository.FoodRepository;
import com.CalorieX.CalorieX_Backend.repository.MealRepository;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MealServiceImpl implements MealsService {

    /*
    This mealservice class is using with third party api called spoonacular"
     */

    private final MealRepository mealRepository;

    private final UserRepository userRepository;

    private final FoodService foodService;

    private final FoodRepository foodRepository;

    private final MealBuilderService mealBuilderService;



    public MealServiceImpl(MealRepository mealRepository, UserRepository userRepository, FoodService foodService, FoodRepository foodRepository, MealBuilderService mealBuilderService) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
        this.foodService = foodService;
        this.foodRepository = foodRepository;
        this.mealBuilderService = mealBuilderService;
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

Meal meal;

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

            meal = mealBuilderService.createMeal(
                    user,
                    food.getName(),
                    (int) Math.round(calories),
                    protein,
                    carbs,
                    fats,
                    addFoodToMealRequest.getFoodId(),
                    FoodSource.LOCAL,
                    amount,
                    addFoodToMealRequest.getUnit(),
                    addFoodToMealRequest.getMealType()
            );



        } else {

            // Fallback to Spoonacular
            FoodDetailsResponse foodDetailsResponse =
                    foodService.getFoodDetails(
                            addFoodToMealRequest.getFoodId(),
                            addFoodToMealRequest.getAmount());

            meal = mealBuilderService.createMeal(
                    user,
                    foodDetailsResponse.getName(),
                    foodDetailsResponse.getCalories().intValue(),
                    foodDetailsResponse.getProtein(),
                    foodDetailsResponse.getCarbs(),
                    foodDetailsResponse.getFat(),
                    addFoodToMealRequest.getFoodId(),
                    FoodSource.SPOONACULAR,
                    addFoodToMealRequest.getAmount(),
                    addFoodToMealRequest.getUnit(),
                    addFoodToMealRequest.getMealType()
            );


        }

        // Save meal
        Meal savedMeal = mealRepository.save(meal);

        // Convert Entity -> DTO
       return mealBuilderService.buildMealResponse(savedMeal);
    }

    @Override
    public List<MealServiceResponse> getRecentMeals() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        List<Meal> meals =
                mealRepository.findTop5ByUserOrderByIdDesc(user);

        return meals.stream()
                .map(meal -> {

                    MealServiceResponse response =
                            new MealServiceResponse();

                    response.setId(meal.getId());
                    response.setMealName(meal.getMealName());
                    response.setCalories(meal.getCalories());
                    response.setProtein(meal.getProtein());
                    response.setCarbs(meal.getCarbs());
                    response.setFats(meal.getFats());
                    response.setMealType(meal.getMealType());
                    response.setDate(meal.getDate());

                    return response;

                })
                .toList();
    }








}
package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.CreateCustomFoodRequest;
import com.CalorieX.CalorieX_Backend.dto.CustomFoodResponse;
import com.CalorieX.CalorieX_Backend.entity.CustomFood;
import com.CalorieX.CalorieX_Backend.entity.FoodSource;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.exception.UserNotFoundException;
import com.CalorieX.CalorieX_Backend.repository.CustomFoodRepository;
import com.CalorieX.CalorieX_Backend.repository.MealRepository;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomFoodServiceImpl implements CustomFoodService  {

    private final CustomFoodRepository customFoodRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final MealBuilderService mealBuilderService;


    public CustomFoodServiceImpl(CustomFoodRepository customFoodRepository, UserRepository userRepository, MealRepository mealRepository, MealBuilderService mealBuilderService) {
        this.customFoodRepository = customFoodRepository;
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
        this.mealBuilderService = mealBuilderService;
    }


    @Override
    public CustomFoodResponse createCustomFood(CreateCustomFoodRequest request){

        // Get loggined in user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();



        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        CustomFood customFood = new CustomFood();

        customFood.setName(request.getName());
        customFood.setCalories(request.getCalories());
        customFood.setProtein(request.getProtein());
        customFood.setCarbs(request.getCarbs());
        customFood.setFats(request.getFats());
        customFood.setFavorite(false);
        customFood.setUser(user);
        customFood.setCreatedAt(LocalDateTime.now());
        customFood.setUpdateAt(LocalDateTime.now());


CustomFood savedCustomFood = customFoodRepository.save(customFood);

        if(Boolean.TRUE.equals(request.getAddTOToday())){
            Double quantity = request.getQuantity();

            double Calories = (savedCustomFood.getCalories() * quantity) /100;

            double protein = (savedCustomFood.getProtein() * quantity ) / 100;

            double carbs = (savedCustomFood.getCarbs() * quantity ) /100;

            double fats = (savedCustomFood.getFats() * quantity )/100;

            Meal meal = mealBuilderService.createMeal(
                    user,
                    savedCustomFood.getName(),
                    (int) Math.round(Calories),
                    protein,
                    carbs,
                    fats,
                    savedCustomFood.getId(),
                    FoodSource.CUSTOM,
                    quantity,
                    "g",
                    request.getMealType()
            );

            mealRepository.save(meal);
        }

        CustomFoodResponse response = new CustomFoodResponse();

        response.setId(savedCustomFood.getId());
        response.setName(savedCustomFood.getName());
        response.setCalories(savedCustomFood.getCalories());
        response.setProtein(savedCustomFood.getProtein());
        response.setCarbs(savedCustomFood.getCarbs());
        response.setFats(savedCustomFood.getFats());
        response.setFavorite(savedCustomFood.getFavorite());

        return response;

    }


}

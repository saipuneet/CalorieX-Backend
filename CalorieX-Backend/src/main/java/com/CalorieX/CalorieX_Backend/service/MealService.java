package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.AddMealRequest;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.repository.MealRepository;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MealService {

    private UserRepository userRepository;

    private final MealRepository mealRepository;

    public MealService(UserRepository userRepository, MealRepository mealRepository) {
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
    }

    public String addMeal(AddMealRequest addMealRequest) {
        // get authenticated user email
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();


        //Find the user from the database
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not Found"));

        //Create meal object

        Meal meal = new Meal();

        //set meal data
        meal.setMealName(addMealRequest.getMealName());
        meal.setCalories(addMealRequest.getCalories());
        meal.setCarbs(addMealRequest.getCarbs());
        meal.setProtein(addMealRequest.getProtein());
        meal.setFats(addMealRequest.getFats());
        meal.setMealType(addMealRequest.getMealType());

        //set current date

        meal.setDate(LocalDate.now());

        //Connect meal with authenticated user

        meal.setUser(user);

        //save the meal data in database

        mealRepository.save(meal);

        return "Meal Added Successfully";

    }
}

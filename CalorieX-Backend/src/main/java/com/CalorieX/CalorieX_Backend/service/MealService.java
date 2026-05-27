package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.AddMealRequest;
import com.CalorieX.CalorieX_Backend.dto.MealResponse;
import com.CalorieX.CalorieX_Backend.dto.NutritionSummaryResoponse;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.repository.MealRepository;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<MealResponse> getMeals(){


        // getting the authentication user email
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        //Find the user from database
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        //Fetch all the meals belong the user
        List<Meal> meals = mealRepository.findByUser(user);

        //Create a MealResponse List
        List<MealResponse> mealResponses = new ArrayList<>();

        //Convert the meals to mealResponse

        for(Meal meal : meals){
            MealResponse mealResponse = new MealResponse();

            mealResponse.setId(meal.getId());
            mealResponse.setMealName(meal.getMealName());
            mealResponse.setCalories(meal.getCalories());
            mealResponse.setProtein(meal.getProtein());
            mealResponse.setFats(meal.getFats());
            mealResponse.setCarbs(meal.getCarbs());
            mealResponse.setMealType(meal.getMealType());
            mealResponse.setDate(meal.getDate());
            mealResponses.add(mealResponse);
        }

           return mealResponses;
    }

    public String deleteMeal(Long mealId){

        // Getting the authenticated email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //Find the user

        User user = userRepository.findByEmail(email).orElseThrow(() ->  new RuntimeException("User not found"));

        //Find the meal with ownership Validation

        Meal meal = mealRepository.findByIdAndUser(mealId,user).orElseThrow(() -> new RuntimeException("Meal not found"));

        mealRepository.delete(meal);

        return "Meal Deleted Successfully";
    }

    public NutritionSummaryResoponse getDailyNutritionSummary(){

        // Get Authentication email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();


        //Get in the authenticated user
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not Found"));

        //Fetch the meal

        List<Meal> meals = mealRepository.findByUser(user);

        int totalCalories =0;

        double totalProtein =0;

        double totalCarbs = 0;

        double totalFats = 0;

        for(Meal meal : meals){
            totalCalories = totalCalories + meal.getCalories();
            totalProtein = totalProtein + meal.getProtein();
            totalFats = totalFats + meal.getFats();
            totalCarbs = totalCarbs + meal.getCarbs();
        }

        NutritionSummaryResoponse nutritionSummaryResoponse = new NutritionSummaryResoponse();

        nutritionSummaryResoponse.setTotalCalories(totalCalories);
        nutritionSummaryResoponse.setTotalProtein(totalProtein);
        nutritionSummaryResoponse.setTotalCarbs(totalCarbs);
        nutritionSummaryResoponse.setTotalFats(totalFats);

        return nutritionSummaryResoponse;

    }
}

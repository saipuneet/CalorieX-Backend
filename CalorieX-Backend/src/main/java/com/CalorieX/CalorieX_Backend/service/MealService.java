package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.*;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.MealType;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.exception.MealNotFoundException;
import com.CalorieX.CalorieX_Backend.exception.UserNotFoundException;
import com.CalorieX.CalorieX_Backend.repository.MealRepository;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public MealPageResponse getMeals(int page, int size){


        // getting the authentication user email
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        //Find the user from database
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        //Craete the pageable Instruction tells the page number and size and sorting

        Pageable pageable = PageRequest.of(page,size, Sort.by("date").descending());

        //Fetch all the meals belong the user  and page instructions
        Page<Meal> mealPage = mealRepository.findByUser(user,pageable);

        //Extract the actual meal data
        List<Meal> meals = mealPage.getContent();

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

        //Create Pagination Dto

        MealPageResponse mealPageResponse = new MealPageResponse();

        mealPageResponse.setMeals(mealResponses);
        mealPageResponse.setCurrentPage(mealPage.getNumber());
        mealPageResponse.setTotalPage(mealPage.getTotalPages());
        mealPageResponse.setTotalElement(mealPageResponse.getTotalElement());

        return mealPageResponse;
    }

    public String deleteMeal(Long mealId){

        // Getting the authenticated email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //Find the user

        User user = userRepository.findByEmail(email).orElseThrow(() ->  new RuntimeException("User not found"));

        //Find the meal with ownership Validation

        Meal meal = mealRepository.findByIdAndUser(mealId,user).orElseThrow(() -> new MealNotFoundException("Meal not found"));

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

    public String updateMeal(Long mealId, UpdateMealRequest updateMealRequest){
        //get authenticated email

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Meal meal = mealRepository.findByIdAndUser(mealId,user).orElseThrow(() -> new MealNotFoundException("Meal not found"));

        meal.setMealName(updateMealRequest.getMealName());
        meal.setCalories(updateMealRequest.getCalories());
        meal.setProtein(updateMealRequest.getProtein());
        meal.setFats(updateMealRequest.getFats());
        meal.setCarbs(updateMealRequest.getCarbs());
        meal.setMealType(updateMealRequest.getMealType());

        mealRepository.save(meal);

        return "Meals update successfully";

    }

    public MealPageResponse searchMeal(String Keyword,int page,int size){


        //Validing the keyword

        if(Keyword == null || Keyword.isBlank()){
            throw new IllegalArgumentException("Keyword Cannot be empty");
        }
        // find the authenticated email
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        //Find the user from user database
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        //Create the Pageable object
        Pageable pageable = PageRequest.of(page,size,Sort.by("date").descending());

        //fetch all the meals of the authenticated user
        Page<Meal> meals = mealRepository.findByUserAndMealNameContainingIgnoreCase(user,Keyword, pageable);

        //Extract the actual data
        List<Meal> mealls = meals.getContent();

         //Convert the entity -> DTO
        //Craete the mealResponse object to store the data
        List<MealResponse> mealResponses = new ArrayList<>();

        for(Meal meal :mealls){
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

        //Build the response
        MealPageResponse mealPageResponse = new MealPageResponse();

        mealPageResponse.setMeals(mealResponses);
        mealPageResponse.setCurrentPage(meals.getNumber());
        mealPageResponse.setTotalPage(meals.getTotalPages());
        mealPageResponse.setTotalElement(meals.getTotalElements());

        return mealPageResponse;

    }

    public MealPageResponse filterMeals(MealType mealType,int page,int size ){

        // get the authenticated user email

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //get the user entity

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        // create the pageable

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        // Call the reposiotory

        Page<Meal> mealPage = mealRepository.findByUserAndMealType(user, mealType, pageable);

        // extract the actudaldata from pagination

        List<Meal> meals = mealPage.getContent();

        // convert the meals into mealResponse

        List<MealResponse> mealResponseList = new ArrayList<>();

        for (Meal meal : meals) {
            MealResponse mealResponse = new MealResponse();

            mealResponse.setId(meal.getId());
            mealResponse.setMealName(meal.getMealName());
            mealResponse.setCalories(meal.getCalories());
            mealResponse.setProtein(meal.getProtein());
            mealResponse.setFats(meal.getFats());
            mealResponse.setCarbs(meal.getCarbs());
            mealResponse.setMealType(meal.getMealType());
            mealResponse.setDate(meal.getDate());
            mealResponseList.add(mealResponse);
        }

        //creating the mealpageResponse
        MealPageResponse mealPageResponse = new MealPageResponse();

        mealPageResponse.setMeals(mealResponseList);
        mealPageResponse.setCurrentPage(mealPage.getNumber());
        mealPageResponse.setTotalPage(mealPage.getTotalPages());
        mealPageResponse.setTotalElement(mealPage.getTotalElements());

        return mealPageResponse;
    }


    public MealPageResponse filterMealsByDate(LocalDate date, int page, int size) {

        ///get the authenticated user email

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //get the user entity

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        // create the pageable

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        // Call the reposiotory

        Page<Meal> mealPage = mealRepository.findByUserAndDate(user,date,pageable);

        // extract the actudaldata from pagination

        List<Meal> meals = mealPage.getContent();

        // convert the meals into mealResponse

        List<MealResponse> mealResponseList = new ArrayList<>();

        for (Meal meal : meals) {
            MealResponse mealResponse = new MealResponse();

            mealResponse.setId(meal.getId());
            mealResponse.setMealName(meal.getMealName());
            mealResponse.setCalories(meal.getCalories());
            mealResponse.setProtein(meal.getProtein());
            mealResponse.setFats(meal.getFats());
            mealResponse.setCarbs(meal.getCarbs());
            mealResponse.setMealType(meal.getMealType());
            mealResponse.setDate(meal.getDate());
            mealResponseList.add(mealResponse);
        }

        //creating the mealpageResponse
        MealPageResponse mealPageResponse = new MealPageResponse();

        mealPageResponse.setMeals(mealResponseList);
        mealPageResponse.setCurrentPage(mealPage.getNumber());
        mealPageResponse.setTotalPage(mealPage.getTotalPages());
        mealPageResponse.setTotalElement(mealPage.getTotalElements());

        return mealPageResponse;
    }


    public MealPageResponse filterMealsByMealTypeAndDate(MealType mealType, LocalDate date, int page, int size) {
        ///get the authenticated user email

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //get the user entity

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        // create the pageable

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());

        // Call the reposiotory

        Page<Meal> mealPage = mealRepository.findByUserAndMealTypeAndDate(user,mealType,date,pageable);

        // extract the actudaldata from pagination

        List<Meal> meals = mealPage.getContent();

        // convert the meals into mealResponse

        List<MealResponse> mealResponseList = new ArrayList<>();

        for (Meal meal : meals) {
            MealResponse mealResponse = new MealResponse();

            mealResponse.setId(meal.getId());
            mealResponse.setMealName(meal.getMealName());
            mealResponse.setCalories(meal.getCalories());
            mealResponse.setProtein(meal.getProtein());
            mealResponse.setFats(meal.getFats());
            mealResponse.setCarbs(meal.getCarbs());
            mealResponse.setMealType(meal.getMealType());
            mealResponse.setDate(meal.getDate());
            mealResponseList.add(mealResponse);
        }

        //creating the mealpageResponse
        MealPageResponse mealPageResponse = new MealPageResponse();

        mealPageResponse.setMeals(mealResponseList);
        mealPageResponse.setCurrentPage(mealPage.getNumber());
        mealPageResponse.setTotalPage(mealPage.getTotalPages());
        mealPageResponse.setTotalElement(mealPage.getTotalElements());

        return mealPageResponse;
    }
}


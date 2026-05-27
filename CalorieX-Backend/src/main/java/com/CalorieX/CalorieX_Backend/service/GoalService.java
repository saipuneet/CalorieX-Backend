package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.GoalProgressResponse;
import com.CalorieX.CalorieX_Backend.dto.GoalResponse;
import com.CalorieX.CalorieX_Backend.dto.SetGoalsRequest;
import com.CalorieX.CalorieX_Backend.entity.Goal;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.repository.GoalRepository;
import com.CalorieX.CalorieX_Backend.repository.MealRepository;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalService {


    //Since GoalService must have access to goal table and user authenticated so we used dependecy injection
    private final GoalRepository goalRepository;

    private final UserRepository userRepository;
    private  final MealRepository mealRepository;


    public GoalService(GoalRepository goalRepository, UserRepository userRepository, MealRepository mealRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
    }

    public String setGoal(SetGoalsRequest setGoalsRequest){
        //Get authenticated user email

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        //Get the user details
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException());

        // Check if goal already exists
        Optional<Goal> existingGoal = goalRepository.findByUser(user);

        //create Goal variable for store the goal
        Goal goal;
        //If goals exists -> update the goal value
        if(existingGoal.isPresent()){
            goal = existingGoal.get();
        }else{

            // if not create a new goal
            goal = new Goal();
            // coonect goal with user
            goal.setUser(user);
        }

        //Setgoal data

        goal.setTargetCalories(setGoalsRequest.getTargetCalories());
        goal.setTargetProtein(setGoalsRequest.getTargetProtein());
        goal.setTargetCarbs(setGoalsRequest.getTargetCarbs());
        goal.setTargetFats(setGoalsRequest.getTargetFats());

        goalRepository.save(goal);

        return " Goal saved successfully";
    }

    public GoalResponse getGoal(){
        // getting the authentication user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // getting the user data from User database by using the method called findbyuser
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // finding the goal of the particular user using the method Findbyuser
        Goal goal = goalRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Goal not found"));

        //Create the goalResponse object to store the data from goal it is like conversion of goal to goalResponse DTO
        GoalResponse goalResponse = new GoalResponse();

        goalResponse.setTargetCalories(goal.getTargetCalories());
        goalResponse.setTargetProtein(goal.getTargetProtein());
        goalResponse.setTargetCarbs(goal.getTargetCarbs());
        goalResponse.setTargetFats(goal.getTargetFats());

        return goalResponse;

    }

    public GoalProgressResponse getGoalProgress(){

        // get authenticated user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        // getting the user data from user Database table
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        // Fetch the user goal
        Goal goal = goalRepository.findByUser(user).orElseThrow(()-> new RuntimeException("Goal not Found"));
        //Fetching the user meal
        List<Meal> meals = mealRepository.findByUser(user);

        int consumedCalories = 0 ;

        for(Meal meal : meals){
            consumedCalories = consumedCalories + meal.getCalories();
        }
        int remainingCalories = goal.getTargetCalories() - consumedCalories;

        double progressPercentage =
                ((double) consumedCalories
                        / goal.getTargetCalories())
                        * 100;

        GoalProgressResponse goalProgressResponse = new GoalProgressResponse();

        goalProgressResponse.setTargetCalories(goal.getTargetCalories());
        goalProgressResponse.setConsumedCalories(consumedCalories);
        goalProgressResponse.setRemainingCalories(remainingCalories);
        goalProgressResponse.setProgressPercentage(progressPercentage);

        return goalProgressResponse;

    }


}

package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.GoalResponse;
import com.CalorieX.CalorieX_Backend.dto.SetGoalsRequest;
import com.CalorieX.CalorieX_Backend.entity.Goal;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.repository.GoalRepository;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoalService {


    //Since GoalService must have access to goal table and user authenticated so we used dependecy injection
    private final GoalRepository goalRepository;

    private final UserRepository userRepository;


    public GoalService(GoalRepository goalRepository, UserRepository userRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
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

        GoalResponse goalResponse = new GoalResponse();

        goalResponse.setTargetCalories(goal.getTargetCalories());
        goalResponse.setTargetProtein(goal.getTargetProtein());
        goalResponse.setTargetCarbs(goal.getTargetCarbs());
        goalResponse.setTargetFats(goal.getTargetFats());

        return goalResponse;

    }


}

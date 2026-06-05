package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.DashboardResponses;
import com.CalorieX.CalorieX_Backend.dto.WeightProgressResponse;
import com.CalorieX.CalorieX_Backend.entity.Goal;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.exception.UserNotFoundException;
import com.CalorieX.CalorieX_Backend.repository.GoalRepository;
import com.CalorieX.CalorieX_Backend.repository.MealRepository;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import com.CalorieX.CalorieX_Backend.repository.WeightLogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DashBoardServiceImpl implements  DashboardService{
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final MealRepository mealRepository;
    private final WeightLogService weightLogService;

    public DashBoardServiceImpl(UserRepository userRepository, GoalRepository goalRepository, MealRepository mealRepository, WeightLogService weightLogService) {
        this.userRepository = userRepository;
        this.goalRepository = goalRepository;
        this.mealRepository = mealRepository;
        this.weightLogService = weightLogService;
    }

    @Override
    public DashboardResponses getDashboard() {

        // Get authenticated user email
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // Get user from database
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        // Get user's goal
        Goal goal = goalRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException("Goal not found"));

        // Get today's meals
        List<Meal> meals = mealRepository
                .findByUserAndDate(user, LocalDate.now());

        // Calculate today's calories
        Integer todayCalories = 0;

        for (Meal meal : meals) {
            todayCalories += meal.getCalories();
        }

        // Goal calories
        Integer goalCalories = goal.getTargetCalories();

        // Remaining calories
        Integer remainingCalories = goalCalories - todayCalories;

        // Get weight progress
        WeightProgressResponse weightProgress =
                weightLogService.getWeightProgress();

        // Build dashboard response
        DashboardResponses dashboardResponse =
                new DashboardResponses();

        dashboardResponse.setTotalCalories(todayCalories);

        dashboardResponse.setGoalCalories(goalCalories);

        dashboardResponse.setRemainingCalories(
                remainingCalories);

        dashboardResponse.setCurrentWeight(
                user.getWeight());

        dashboardResponse.setWeightChange(
                weightProgress.getWeightChange());

        dashboardResponse.setMealsLoggedToday(
                meals.size());

        return dashboardResponse;
    }
}

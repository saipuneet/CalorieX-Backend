package com.CalorieX.CalorieX_Backend.controller;


import com.CalorieX.CalorieX_Backend.dto.GoalResponse;
import com.CalorieX.CalorieX_Backend.dto.SetGoalsRequest;
import com.CalorieX.CalorieX_Backend.service.GoalService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goals")
public class GoalController {

    // in order to acess the goalservice method we need the goalservice class using the dependency injection
    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/setGoal")
    public String setGoal(@Valid @RequestBody SetGoalsRequest setGoalsRequest){
        return goalService.setGoal(setGoalsRequest);
    }

    @GetMapping("/getGoal")
    public GoalResponse getGoal(){
        return goalService.getGoal();
    }
}

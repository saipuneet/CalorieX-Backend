package com.CalorieX.CalorieX_Backend.controller;


import com.CalorieX.CalorieX_Backend.dto.AddMealRequest;
import com.CalorieX.CalorieX_Backend.service.MealService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @PostMapping("/addmeal")
    public String addMeal(@Valid @RequestBody AddMealRequest addMealRequest){
        return mealService.addMeal(addMealRequest);
    }


}

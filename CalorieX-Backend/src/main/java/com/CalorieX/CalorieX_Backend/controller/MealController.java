package com.CalorieX.CalorieX_Backend.controller;


import com.CalorieX.CalorieX_Backend.dto.AddMealRequest;
import com.CalorieX.CalorieX_Backend.dto.MealResponse;
import com.CalorieX.CalorieX_Backend.dto.NutritionSummaryResoponse;
import com.CalorieX.CalorieX_Backend.dto.UpdateMealRequest;
import com.CalorieX.CalorieX_Backend.service.MealService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getMeal")
    public List<MealResponse> getMeal(@RequestParam int page,@RequestParam int Size){
        return mealService.getMeals(page, Size);
    }

    @DeleteMapping("/{mealId}")
    public String deleteMeal(@PathVariable Long mealId){
        return mealService.deleteMeal(mealId);
    }

    @GetMapping("/summary")
    public NutritionSummaryResoponse getDailyNutritionSummary(){
        return mealService.getDailyNutritionSummary();
    }

    @PutMapping("/{mealId}")
    public String updateMeal(@PathVariable Long mealId, @Valid @RequestBody UpdateMealRequest updateMealRequest){
        return mealService.updateMeal(mealId,updateMealRequest);
    }


}

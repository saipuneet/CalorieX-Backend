package com.CalorieX.CalorieX_Backend.controller;

import com.CalorieX.CalorieX_Backend.dto.FoodDetailsResponse;
import com.CalorieX.CalorieX_Backend.dto.FoodSearchResponse;
import com.CalorieX.CalorieX_Backend.service.FoodServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods")
public class FoodController {

    private final FoodServiceImpl foodService;


    public FoodController(FoodServiceImpl foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/search")
    public List<FoodSearchResponse> searchFoods(@RequestParam String query){

        return foodService.searchFoods(query);

    }

    @GetMapping("/{id}")
    public FoodDetailsResponse getFoodDetails(@PathVariable Long id, @RequestParam(defaultValue = "100") Double amount){
        return foodService.getFoodDetails(id,amount);
    }
}

package com.CalorieX.CalorieX_Backend.controller;

import com.CalorieX.CalorieX_Backend.dto.CreateCustomFoodRequest;
import com.CalorieX.CalorieX_Backend.dto.CustomFoodResponse;
import com.CalorieX.CalorieX_Backend.service.CustomFoodService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/custom-foods")
public class CustomFoodController {

    private final CustomFoodService customFoodService;

    public CustomFoodController(CustomFoodService customFoodService) {
        this.customFoodService = customFoodService;
    }

    @PostMapping
    public CustomFoodResponse customFoodResponse(@Valid @RequestBody CreateCustomFoodRequest request){
        return customFoodService.createCustomFood(request);

    }
}

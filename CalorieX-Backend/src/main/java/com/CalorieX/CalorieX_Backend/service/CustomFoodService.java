package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.CreateCustomFoodRequest;
import com.CalorieX.CalorieX_Backend.dto.CustomFoodResponse;
import org.springframework.stereotype.Service;


public interface CustomFoodService {

    CustomFoodResponse createCustomFood(CreateCustomFoodRequest request);
}

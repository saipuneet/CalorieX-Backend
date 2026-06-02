package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.FoodDetailsResponse;
import com.CalorieX.CalorieX_Backend.dto.FoodSearchResponse;
import org.w3c.dom.stylesheets.LinkStyle;

import java.awt.*;
import java.util.List;

public interface FoodService {

    List<FoodSearchResponse> searchFoods(String query);

    FoodDetailsResponse getFoodDetails(Long id, Double amount);


}

package com.CalorieX.CalorieX_Backend.dto;

import java.util.List;

public class FoodApiResponse {

    private List<FoodSearchResponse> results;

    public FoodApiResponse(){

    }

    public List<FoodSearchResponse> getResults() {
        return results;
    }

    public void setResults(List<FoodSearchResponse> results) {
        this.results = results;
    }
}

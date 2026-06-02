package com.CalorieX.CalorieX_Backend.dto;

public class IngredientInformationResponse {

    private Long id;
    private String name;
    private Nutrition nutrition;

    public IngredientInformationResponse(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }
}

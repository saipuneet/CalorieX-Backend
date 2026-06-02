package com.CalorieX.CalorieX_Backend.dto;

import java.util.List;

public class Nutrition {

    private List<Nutrient> nutrients;

    public Nutrition(){

    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public void setNutrients(List<Nutrient> nutrients) {
        this.nutrients = nutrients;
    }
}

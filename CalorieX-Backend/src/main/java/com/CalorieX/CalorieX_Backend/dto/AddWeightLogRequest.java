package com.CalorieX.CalorieX_Backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddWeightLogRequest {
    @NotNull(message = "Weight is required")
    @Positive(message = "weight must be greater than 0")
    private Double weight;

    public AddWeightLogRequest(){

    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}

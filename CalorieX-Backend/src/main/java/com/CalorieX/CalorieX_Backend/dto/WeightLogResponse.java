package com.CalorieX.CalorieX_Backend.dto;

import java.time.LocalDate;

public class WeightLogResponse {

    private Long id;

    private Double weight;

    private LocalDate date;

    public WeightLogResponse(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

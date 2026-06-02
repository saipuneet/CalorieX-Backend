package com.CalorieX.CalorieX_Backend.dto;

import org.apache.commons.lang3.DoubleRange;

public class Nutrient {

    private String name;
    private Double amount;
    private String unit;

    public Nutrient(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

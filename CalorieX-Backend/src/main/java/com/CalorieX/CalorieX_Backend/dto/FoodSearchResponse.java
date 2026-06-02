package com.CalorieX.CalorieX_Backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FoodSearchResponse {

    private Long id;

    private String name;

    private String image;

    public FoodSearchResponse(){

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

package com.CalorieX.CalorieX_Backend.dto;

import java.util.List;

public class MealPageResponse {

    private List<MealResponse> meals;

    private int currentPage;

    private int totalPage;

    private long totalElement;

    public MealPageResponse(){

    }

    public MealPageResponse(List<MealResponse> meals, int currentPage, int totalPage, long totalElement) {
        this.meals = meals;
        this.currentPage = currentPage;
        this.totalPage = totalPage;
        this.totalElement = totalElement;
    }

    public List<MealResponse> getMeals() {
        return meals;
    }

    public void setMeals(List<MealResponse> meals) {
        this.meals = meals;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(long totalElement) {
        this.totalElement = totalElement;
    }
}

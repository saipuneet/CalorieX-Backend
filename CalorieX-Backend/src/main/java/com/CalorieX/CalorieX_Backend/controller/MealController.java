package com.CalorieX.CalorieX_Backend.controller;


import com.CalorieX.CalorieX_Backend.dto.*;
import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.MealType;
import com.CalorieX.CalorieX_Backend.service.MealService;
import com.CalorieX.CalorieX_Backend.service.MealServiceImpl;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    private final MealService mealService;
    private final MealServiceImpl mealServices;

    public MealController(MealService mealService, MealServiceImpl mealServices) {

        this.mealService = mealService;
        this.mealServices = mealServices;
    }

    @PostMapping("/addmeal")
    public String addMeal(@Valid @RequestBody AddMealRequest addMealRequest){
        return mealService.addMeal(addMealRequest);
    }

    @GetMapping("/getMeal")
    public MealPageResponse getMeal(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int Size){
        return mealService.getMeals(page, Size);
    }

    @DeleteMapping("/{mealId}")
    public String deleteMeal(@PathVariable Long mealId){
        return mealService.deleteMeal(mealId);
    }

    @GetMapping("/summary")
    public NutritionSummaryResoponse getDailyNutritionSummary(){
        return mealService.getDailyNutritionSummary();
    }

    @PutMapping("/{mealId}")
    public String updateMeal(@PathVariable Long mealId, @Valid @RequestBody UpdateMealRequest updateMealRequest){
        return mealService.updateMeal(mealId,updateMealRequest);
    }

    @GetMapping("/search")
    public MealPageResponse searchMeals(@RequestParam String Keyword,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){

        return mealService.searchMeal(Keyword,page,size);

    }


    @GetMapping("/filter")
    public MealPageResponse filterMeals(@RequestParam MealType mealType,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        return mealService.filterMeals(mealType,page,size);
    }

    @GetMapping("/filter/date")
    public MealPageResponse filterMealsBydate(@RequestParam LocalDate date,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size){
        return mealService.filterMealsByDate(date,page,size);
    }

    @GetMapping("/filter/mealtype-date")
    public MealPageResponse filterMealsByMealTypeAndDate(@RequestParam MealType mealType,
                                                         @RequestParam LocalDate date,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size){
        return mealService.filterMealsByMealTypeAndDate(mealType,date,page,size);
    }

    @PostMapping("/add-from-food")
    public MealServiceResponse addFoodToMeals(@RequestBody AddFoodToMealRequest addFoodToMealRequest){
        return mealServices.addFoodToMeal(addFoodToMealRequest);
    }

    @GetMapping("/recent")
    public List<MealServiceResponse> getRecentMeals() {

        return mealServices.getRecentMeals();

    }


}

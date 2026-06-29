package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.*;
import com.CalorieX.CalorieX_Backend.entity.Food;
import com.CalorieX.CalorieX_Backend.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceImpl implements FoodService{



    //Injecting the resttemplate
    private final RestTemplate restTemplate;

    private final FoodRepository foodRepository;
    @Value("${spoonacular.api.key}")
    private String apiKey;
    ;


    public FoodServiceImpl(RestTemplate restTemplate, FoodRepository foodRepository){
        
        this.restTemplate = restTemplate;
        this.foodRepository = foodRepository;
    }
    
    




    @Override
    public List<FoodSearchResponse> searchFoods(String query) {

        List<Food> foods = foodRepository.findByNameContainingIgnoreCase(query);

        if(!foods.isEmpty()){
            return foods.stream().map(food -> {
                FoodSearchResponse response = new FoodSearchResponse();

                response.setId(food.getId());
                response.setName(food.getName());
                response.setCalories(food.getCalories());
                response.setProtein(food.getProtein());
                response.setCarbs(food.getCarbs());
                response.setFats(food.getFats());
                return response;
            }).toList();
        }


         // build the url
       
        String url = "https://api.spoonacular.com/food/ingredients/search?query="
                + query
                + "&number=10&apiKey="
                + apiKey;


        //Call the api
        //Let understand this
        /*
        restTemplate is the bean spring injected
        getForObject means send the get request and receieve response and convert the response into object
        url tells the spring where to send the request
        Foodapiresponse.class is convert the json into Foodapiresponse

         */

        FoodApiResponse foodApiResponse = restTemplate.getForObject(url, FoodApiResponse.class);

        if(foodApiResponse == null || foodApiResponse.getResults() == null){
            return new ArrayList<>();
        }

        return foodApiResponse.getResults();
    }

    @Override
    public FoodDetailsResponse getFoodDetails(Long id,Double amount){

        Optional<Food> foodOptional= foodRepository.findById(id);
        if(foodOptional.isPresent()){
            Food food = foodOptional.get();
            double factor = amount /100.0;
            FoodDetailsResponse response = new FoodDetailsResponse();

            response.setId(food.getId());
            response.setName(food.getName());
            response.setCalories(food.getCalories() * factor);
            response.setProtein(food.getProtein() * factor);
            response.setCarbs(food.getCarbs() * factor);
            response.setFat(food.getFats() * factor);
            return response;
        }





        //Build the url
        String url = "https://api.spoonacular.com/food/ingredients/"
                + id
                + "/information?amount=" + amount + "&unit=gram&apiKey="
                + apiKey;


        // call the external api using resttemplate

        IngredientInformationResponse ingredientInformationResponse = restTemplate.getForObject(url, IngredientInformationResponse.class);


        if(ingredientInformationResponse == null){
            return null;
        }

        // creating the fooddetailsresponse object
        FoodDetailsResponse foodDetailsResponse = new FoodDetailsResponse();

        foodDetailsResponse.setId(ingredientInformationResponse.getId());
        foodDetailsResponse.setName(ingredientInformationResponse.getName());

        // if there is no nutrition its like a validation not to crash the application
        if (ingredientInformationResponse.getNutrition() == null) {
            return foodDetailsResponse;
        }


        for(Nutrient nutrient : ingredientInformationResponse.getNutrition().getNutrients()){
            System.out.println(
                    nutrient.getName() + " = " + nutrient.getAmount()
            );

            if("Calories".equals(nutrient.getName())){
                foodDetailsResponse.setCalories(nutrient.getAmount());
            }
            if("Protein".equals(nutrient.getName())){
                foodDetailsResponse.setProtein(nutrient.getAmount());
            }

            if("Carbohydrates".equals(nutrient.getName())){
                foodDetailsResponse.setCarbs(nutrient.getAmount());
            }

            if("Fat".equals(nutrient.getName())){
                foodDetailsResponse.setFat(nutrient.getAmount());
            }
        }
        return foodDetailsResponse;

    }


}

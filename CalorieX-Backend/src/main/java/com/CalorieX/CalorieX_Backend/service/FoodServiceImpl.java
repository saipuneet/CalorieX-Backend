package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService{



    //Injecting the resttemplate
    private final RestTemplate restTemplate;
    @Value("${spoonacular.api.key}")
    private String apiKey;
    ;


    public FoodServiceImpl(RestTemplate restTemplate){
        
        this.restTemplate = restTemplate;
    }
    
    




    @Override
    public List<FoodSearchResponse> searchFoods(String query) {


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

        //Build the url
        String url = "https://api.spoonacular.com/food/ingredients/"
                + id
                + "/information?amount=" + amount + "&apiKey="
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

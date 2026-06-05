package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.AddWeightLogRequest;
import com.CalorieX.CalorieX_Backend.dto.WeightLogResponse;
import com.CalorieX.CalorieX_Backend.dto.WeightProgressResponse;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.entity.WeightLog;
import com.CalorieX.CalorieX_Backend.exception.UserNotFoundException;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import com.CalorieX.CalorieX_Backend.repository.WeightLogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeightLogServiceImpl implements WeightLogService{

    private final WeightLogRepository weightLogRepository;

    private final UserRepository userRepository;


    public WeightLogServiceImpl(WeightLogRepository weightLogRepository, UserRepository userRepository) {
        this.weightLogRepository = weightLogRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WeightLogResponse addWeight(AddWeightLogRequest addWeightLogRequest){
        // get the authenticated user

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //Call the user from the user database

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        Optional<WeightLog> existingweightLog = weightLogRepository.findByUserAndDate(user, LocalDate.now());

        //Creating or updating the weight

        WeightLog weightLog;

        if(existingweightLog.isPresent()){
            weightLog = existingweightLog.get();

            weightLog.setWeight(addWeightLogRequest.getWeight());
        }else{
            weightLog = new WeightLog();
            weightLog.setWeight(addWeightLogRequest.getWeight());
            weightLog.setDate(LocalDate.now());
            weightLog.setUser(user);
        }

        //Save the weight Log in the datbases

        WeightLog savedWeightLog = weightLogRepository.save(weightLog);

        //Updating the user current weight

        user.setWeight(addWeightLogRequest.getWeight());
        userRepository.save(user);

        //Convert the entity into dto

        WeightLogResponse weightLogResponse = new WeightLogResponse();

        weightLogResponse.setId(savedWeightLog.getId());
        weightLogResponse.setWeight(savedWeightLog.getWeight());
        weightLogResponse.setDate(savedWeightLog.getDate());

        System.out.println("Saved Entity Date = " + savedWeightLog.getDate());

        System.out.println("DTO Date = " + weightLogResponse.getDate());

        return weightLogResponse;

    }


    @Override
    public List<WeightLogResponse> getWeightHistory() {
        // get the authenticated user
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //Find the user
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        //Get the weightLogs

        List<WeightLog> weightLog = weightLogRepository.findByUserOrderByDateDesc(user);

        //Convert the entity into Dto

        List<WeightLogResponse> weightLogResponses = new ArrayList<>();

        for (WeightLog weightLog1 : weightLog) {
            WeightLogResponse weightLogResponse = new WeightLogResponse();
            weightLogResponse.setId(weightLog1.getId());
            weightLogResponse.setWeight(weightLog1.getWeight());
            weightLogResponse.setDate(weightLog1.getDate());
            weightLogResponses.add(weightLogResponse);
        }


        return weightLogResponses;

    }

    @Override
    public WeightProgressResponse getWeightProgress(){
        //Get the authenticated email

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //get the user details from the user database

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        //get the weightLogs of the user in ascending order

        List<WeightLog> weightLogs = weightLogRepository.findByUserOrderByDateAsc(user);

        //Edge cases if user doesnot have any weightlogs
        if(weightLogs.isEmpty()){
            throw new RuntimeException("No weight Logs is found");
        }

        //Starting weight since the weightlog in ascending order

        Double startingweight = weightLogs.get(0).getWeight();

        //Current weight since we already save the current weight in the user table

        Double currentweight = user.getWeight();

        //Weight Changes

        Double WeightChange = currentweight - startingweight;

        //Build the response

        WeightProgressResponse weightProgressResponse = new WeightProgressResponse();

        weightProgressResponse.setStartingWeight(startingweight);
        weightProgressResponse.setCurrentWeight(currentweight);
        weightProgressResponse.setWeightChange(WeightChange);

        return weightProgressResponse;
    }


}

package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.AddWeightLogRequest;
import com.CalorieX.CalorieX_Backend.dto.WeightLogResponse;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.entity.WeightLog;
import com.CalorieX.CalorieX_Backend.exception.UserNotFoundException;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import com.CalorieX.CalorieX_Backend.repository.WeightLogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        return weightLogResponse;

    }
}

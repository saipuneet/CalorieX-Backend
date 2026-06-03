package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.AddWeightLogRequest;
import com.CalorieX.CalorieX_Backend.dto.WeightLogResponse;

public interface WeightLogService {

    WeightLogResponse addWeight(AddWeightLogRequest request);
}

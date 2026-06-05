package com.CalorieX.CalorieX_Backend.controller;


import com.CalorieX.CalorieX_Backend.dto.AddWeightLogRequest;
import com.CalorieX.CalorieX_Backend.dto.WeightLogResponse;
import com.CalorieX.CalorieX_Backend.dto.WeightProgressResponse;
import com.CalorieX.CalorieX_Backend.service.WeightLogService;
import com.CalorieX.CalorieX_Backend.service.WeightLogServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weights")
public class WeightController {

    private final WeightLogService weightLogService;

    public WeightController(
            WeightLogService weightLogService) {

        this.weightLogService = weightLogService;
    }

    @PostMapping
    public WeightLogResponse addWeight(
            @RequestBody AddWeightLogRequest request) {

        return weightLogService.addWeight(request);
    }

    @GetMapping("/history")
    public List<WeightLogResponse> history(){

        return weightLogService.getWeightHistory();
    }

    @GetMapping("/progress")
    public WeightProgressResponse getWeightProgress(){
        return weightLogService.getWeightProgress();
    }
}

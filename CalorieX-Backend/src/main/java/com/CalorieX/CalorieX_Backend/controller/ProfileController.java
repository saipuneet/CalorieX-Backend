package com.CalorieX.CalorieX_Backend.controller;


import com.CalorieX.CalorieX_Backend.dto.ProfileResponse;
import com.CalorieX.CalorieX_Backend.dto.UpdateProfileRequest;
import com.CalorieX.CalorieX_Backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @Autowired
    private UserService userService;



    @PutMapping("/profile")
    public String updateProfile(@Valid @RequestBody UpdateProfileRequest updateProfileRequest){
       return userService.updateProfile(updateProfileRequest);
    }

    @GetMapping("/profile")
    public ProfileResponse getProfile() {

        return userService.getProfile();
    }



}

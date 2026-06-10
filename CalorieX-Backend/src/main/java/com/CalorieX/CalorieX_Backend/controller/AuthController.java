package com.CalorieX.CalorieX_Backend.controller;

import com.CalorieX.CalorieX_Backend.dto.AuthResponse;
import com.CalorieX.CalorieX_Backend.dto.LoginRequest;
import com.CalorieX.CalorieX_Backend.dto.RegisterRequest;
import com.CalorieX.CalorieX_Backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest registerRequest){
        userService.register(registerRequest);
        return "User Registered Successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }


}

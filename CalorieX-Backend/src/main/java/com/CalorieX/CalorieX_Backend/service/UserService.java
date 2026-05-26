package com.CalorieX.CalorieX_Backend.service;

import com.CalorieX.CalorieX_Backend.dto.*;
import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.repository.UserRepository;
import com.CalorieX.CalorieX_Backend.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private final JwtService jwtService;


    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }


    public User register(@Valid RegisterRequest registerRequest) {

        //check email alread exists
        if(userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Email already exists");
        }

        //Convert DTO -> Entity
        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(
                passwordEncoder.encode(registerRequest.getPassword())
        );
        user.setAge(registerRequest.getAge());
        user.setHeight(registerRequest.getHeight());
        user.setWeight(registerRequest.getWeight());
        user.setGender(registerRequest.getGender());
        user.setGoal(registerRequest.getGoal());

        return userRepository.save(user);
    }

    public AuthResponse login(@Valid LoginRequest loginRequest) {


        // Check if the email is exist or not
        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());

        // if email is not exist
        if(optionalUser.isEmpty()){
            throw new RuntimeException("Email is not found");
        }

        //Get actual user object
        User user = optionalUser.get();


        //Verify the password
        boolean passwordMatches = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword());

        // if password is not match
        if(!passwordMatches){
            throw new RuntimeException("Password is incorrect");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);

    }

    public String updateProfile(UpdateProfileRequest updateProfileRequest){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not Found"));

        user.setAge(updateProfileRequest.getAge());

        user.setHeight(updateProfileRequest.getHeight());

        user.setWeight(updateProfileRequest.getWeight());

        user.setGoal(updateProfileRequest.getGoal());

        user.setActivityLevel(updateProfileRequest.getActivityLevel());

        userRepository.save(user);

        return "Profile Updated Sucessfully";
    }

    public ProfileResponse getProfile(){

        //Get authenticated user email from JWT
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //Find the user from the database
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        //create response DTO
        ProfileResponse profileResponse = new ProfileResponse();

        //setData into DTO

        profileResponse.setName(user.getName());
        profileResponse.setEmail(user.getEmail());
        profileResponse.setAge(user.getAge());
        profileResponse.setHeight(user.getHeight());

        profileResponse.setWeight(user.getWeight());

        profileResponse.setGoal(user.getGoal());

        profileResponse.setActivityLevel(
                user.getActivityLevel()
        );

        // Return response
        return profileResponse;
    }
}

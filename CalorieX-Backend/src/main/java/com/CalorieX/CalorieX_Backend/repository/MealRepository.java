package com.CalorieX.CalorieX_Backend.repository;

import com.CalorieX.CalorieX_Backend.entity.Meal;
import com.CalorieX.CalorieX_Backend.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {

    //Find the user meal with pagination
    Page<Meal> findByUser(User user,Pageable pageable);

    //Find the user from user database
    List<Meal> findByUser(User user);

   Optional<Meal> findByIdAndUser(Long id,User user);
}

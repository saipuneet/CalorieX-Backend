package com.CalorieX.CalorieX_Backend.repository;

import com.CalorieX.CalorieX_Backend.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {
}

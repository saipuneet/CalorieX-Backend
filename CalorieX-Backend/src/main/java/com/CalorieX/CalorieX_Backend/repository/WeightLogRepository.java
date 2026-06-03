package com.CalorieX.CalorieX_Backend.repository;

import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.entity.WeightLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface WeightLogRepository extends JpaRepository<WeightLog,Long> {

   Optional<WeightLog> findByUserAndDate(User user, LocalDate date);
}

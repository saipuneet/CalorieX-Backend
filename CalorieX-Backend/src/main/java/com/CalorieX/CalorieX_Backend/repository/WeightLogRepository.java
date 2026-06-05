package com.CalorieX.CalorieX_Backend.repository;

import com.CalorieX.CalorieX_Backend.entity.User;
import com.CalorieX.CalorieX_Backend.entity.WeightLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeightLogRepository extends JpaRepository<WeightLog,Long> {


    // fetching the data by user and data
   Optional<WeightLog> findByUserAndDate(User user, LocalDate date);

   //List of the weightlog of a particular user in Descending order date wise
    List<WeightLog> findByUserOrderByDateDesc(User user);

    List<WeightLog> findByUserOrderByDateAsc(User user);
}

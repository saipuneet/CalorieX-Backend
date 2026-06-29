package com.CalorieX.CalorieX_Backend.repository;

import com.CalorieX.CalorieX_Backend.entity.CustomFood;
import com.CalorieX.CalorieX_Backend.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomFoodRepository extends JpaRepository<CustomFood, Long> {

    List<CustomFood> findByUser(User user);

    Optional<CustomFood> findByIdAndUser(Long id, User user);
}

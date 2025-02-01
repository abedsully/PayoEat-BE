package com.example.PayoEat.repository;

import com.example.PayoEat.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    boolean existsByName(String name);
    Optional <Restaurant> findByIdAndIsActiveTrue(Long id);
    List<Restaurant> findByIsActiveTrue();
}

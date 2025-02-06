package com.example.PayoEat.repository;

import com.example.PayoEat.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findByRestaurantId(Long restaurantId);
}

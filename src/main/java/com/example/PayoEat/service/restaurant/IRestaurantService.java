package com.example.PayoEat.service.restaurant;

import com.example.PayoEat.model.Restaurant;
import com.example.PayoEat.request.AddRestaurantRequest;

import java.util.List;

public interface IRestaurantService {
    Restaurant addRestaurant(AddRestaurantRequest request);
    Restaurant getRestaurantById(Long id);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> findRestaurantByName(String name);
}

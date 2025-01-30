package com.example.PayoEat.service.restaurant;

import com.example.PayoEat.model.Restaurant;
import com.example.PayoEat.request.AddRestaurantRequest;
import com.example.PayoEat.request.UpdateRestaurantRequest;

import java.util.List;

public interface IRestaurantService {
    Restaurant addRestaurant(AddRestaurantRequest request);
    Restaurant getRestaurantById(Long id);
    Restaurant updateRestaurant(UpdateRestaurantRequest request, Long restaurantId);
    List<Restaurant> getAllRestaurants();
    void deleteRestaurantById(Long id);
}

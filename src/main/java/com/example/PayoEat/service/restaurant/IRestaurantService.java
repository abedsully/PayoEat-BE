package com.example.PayoEat.service.restaurant;

import com.example.PayoEat.model.Restaurant;
import com.example.PayoEat.request.restaurant.AddRestaurantRequest;
import com.example.PayoEat.request.restaurant.UpdateRestaurantRequest;
import com.example.PayoEat.dto.RestaurantDto;

import java.util.List;

public interface IRestaurantService {
    Restaurant addRestaurant(AddRestaurantRequest request);
    Restaurant updateRestaurant(Long restaurantId, UpdateRestaurantRequest request);
    void deleteRestaurant(Long restaurantId);
    Restaurant getRestaurantById(Long id);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> findRestaurantByName(String name);
    RestaurantDto convertToDto(Restaurant restaurant);
    List<RestaurantDto> getConvertedRestaurants (List<Restaurant> restaurants);
}

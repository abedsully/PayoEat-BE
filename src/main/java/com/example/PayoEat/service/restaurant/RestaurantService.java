package com.example.PayoEat.service.restaurant;

import com.example.PayoEat.exceptions.AlreadyExistException;
import com.example.PayoEat.model.Restaurant;
import com.example.PayoEat.repository.RestaurantRepository;
import com.example.PayoEat.request.AddRestaurantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public Restaurant addRestaurant(AddRestaurantRequest request) {
        if (restaurantExists(request.getName())) {
            throw new AlreadyExistException(request.getName() + " already exists");
        }

        return restaurantRepository.save(createRestaurant(request));
    }

    private boolean restaurantExists(String name) {
        return restaurantRepository.existsByName(name);
    }

    private Restaurant createRestaurant(AddRestaurantRequest request) {
        return new Restaurant(
                request.getName(),
                request.getRating(),
                request.getDescription()
        );
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
}

package com.example.PayoEat.service.restaurant;

import com.example.PayoEat.exceptions.AlreadyExistException;
import com.example.PayoEat.exceptions.NotFoundException;
import com.example.PayoEat.model.Restaurant;
import com.example.PayoEat.repository.RestaurantRepository;
import com.example.PayoEat.request.restaurant.AddRestaurantRequest;
import com.example.PayoEat.request.restaurant.UpdateRestaurantRequest;
import com.example.PayoEat.dto.RestaurantDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class RestaurantService implements IRestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Override
    public Restaurant addRestaurant(AddRestaurantRequest request) {
        if (restaurantExists(request.getName())) {
            throw new AlreadyExistException(request.getName() + " already exists");
        }

        return restaurantRepository.save(createRestaurant(request));
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, UpdateRestaurantRequest request) {
        return restaurantRepository.findByIdAndIsActiveTrue(restaurantId)
                .map(existingRestaurant -> updateExistingRestaurant(existingRestaurant, request))
                .map(restaurantRepository::save)
                .orElseThrow(() -> new NotFoundException("Restaurant not found!"));
    }

    private Restaurant updateExistingRestaurant(Restaurant existingRestaurant, UpdateRestaurantRequest request) {
        if ((request.getName() == null || request.getName().isEmpty()) &&
                (request.getDescription() == null || request.getDescription().isEmpty()) &&
                request.getRating() == null) {
            throw new IllegalArgumentException("No valid fields provided to update the restaurant.");
        }

        if (request.getName() != null && !request.getName().isEmpty()) {
            existingRestaurant.setName(request.getName());
        }

        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            existingRestaurant.setDescription(request.getDescription());
        }

        if (request.getRating() != null) {
            existingRestaurant.setRating(request.getRating());
        }

        existingRestaurant.setUpdatedAt(LocalDateTime.now());

        return existingRestaurant;
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.findByIdAndIsActiveTrue(restaurantId)
                .map(currentRestaurant -> {
                    deleteExistingRestaurant(currentRestaurant);
                    return restaurantRepository.save(currentRestaurant);
                })
                .orElseThrow(() -> new NotFoundException("Restaurant not found!"));
    }

    private void deleteExistingRestaurant(Restaurant existingRestaurant) {
        existingRestaurant.setUpdatedAt(LocalDateTime.now());
        existingRestaurant.setIsActive(false);
    }

    private boolean restaurantExists(String name) {
        return restaurantRepository.existsByName(name);
    }

    private Restaurant createRestaurant(AddRestaurantRequest request) {
        Restaurant restaurant = new Restaurant(
                request.getName(),
                request.getRating(),
                request.getDescription()
        );

        restaurant.setCreatedAt(LocalDateTime.now());
        restaurant.setUpdatedAt(null);
        restaurant.setIsActive(true);

        return restaurant;
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new NotFoundException("Restaurant not found with id: " + id));
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findByIsActiveTrue();
    }

    @Override
    public List<Restaurant> findRestaurantByName(String name) {
        return restaurantRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public RestaurantDto convertToDto(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDto.class);
    }

    @Override
    public List<RestaurantDto> getConvertedRestaurants(List<Restaurant> restaurants) {
        return restaurants.stream().map(this::convertToDto).toList();
    }
}

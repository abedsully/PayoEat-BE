package com.example.PayoEat.controller;

import com.example.PayoEat.model.Restaurant;
import com.example.PayoEat.request.restaurant.AddRestaurantRequest;
import com.example.PayoEat.request.restaurant.UpdateRestaurantRequest;
import com.example.PayoEat.response.ApiResponse;
import com.example.PayoEat.service.restaurant.IRestaurantService;
import com.example.PayoEat.dto.RestaurantDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/restaurant")
@Tag(name = "Restaurant Controller", description = "Endpoint for managing restaurants")
public class RestaurantController {

    private final IRestaurantService restaurantService;

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant by ID", description = "Returns a single restaurant based on its ID")
    public ResponseEntity<ApiResponse> getRestaurantById(@PathVariable Long id) {
        try {
            Restaurant restaurant = restaurantService.getRestaurantById(id);
            RestaurantDto convertedRestaurant = restaurantService.convertToDto(restaurant);
            return ResponseEntity.ok(new ApiResponse("Restaurant found", convertedRestaurant));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add-restaurant")
    @Operation(summary = "Add Restaurant", description = "Add restaurant by request")
    public ResponseEntity<ApiResponse> addRestaurant(@RequestBody AddRestaurantRequest request) {
        try {
            Restaurant newRestaurant = restaurantService.addRestaurant(request);
            RestaurantDto convertedRestaurant = restaurantService.convertToDto(newRestaurant);
            return ResponseEntity.ok(new ApiResponse("Restaurant added successfully", convertedRestaurant));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error: " + e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Restaurants", description = "Getting all available restaurants")
    public ResponseEntity<ApiResponse> getAllRestaurants() {
        try {
            List<Restaurant> restaurants = restaurantService.getAllRestaurants();
            List<RestaurantDto> convertedRestaurants = restaurantService.getConvertedRestaurants(restaurants);
            return ResponseEntity.ok(new ApiResponse("Found", convertedRestaurants));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/get-restaurant-by-name")
    @Operation(summary = "Get Restaurants By Name", description = "Getting list of restaurants based on their name")
    public ResponseEntity<ApiResponse> getRestaurantByName(@RequestParam String name) {
        try {
            List<Restaurant> restaurants = restaurantService.findRestaurantByName(name);

            if (restaurants.isEmpty()) {
                return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No restaurants found with name: " + name, null));
            }

            List<RestaurantDto> convertedRestaurants = restaurantService.getConvertedRestaurants(restaurants);

            return ResponseEntity.ok(new ApiResponse("Found", convertedRestaurants));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/update-restaurant/{id}")
    @Operation(summary = "Updating restaurant by id", description = "API for updating restaurant")
    public ResponseEntity<ApiResponse> updateRestaurant(@PathVariable Long id, @RequestBody UpdateRestaurantRequest request) {
        try {
            Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, request);
            RestaurantDto convertedRestaurant = restaurantService.convertToDto(updatedRestaurant);
            return ResponseEntity.ok(new ApiResponse("Restaurant updated successfully", convertedRestaurant));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete-restaurant/{id}")
    @Operation(summary = "Deleting restaurant by id", description = "API for deleting restaurant")
    public ResponseEntity<ApiResponse> deleteRestaurant(@PathVariable Long id) {
        try {
            restaurantService.deleteRestaurant(id);
            return ResponseEntity.ok(new ApiResponse("Restaurant deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

}
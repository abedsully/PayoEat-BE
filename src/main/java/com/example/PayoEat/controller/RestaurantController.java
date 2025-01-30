package com.example.PayoEat.controller;

import com.example.PayoEat.model.Restaurant;
import com.example.PayoEat.request.AddRestaurantRequest;
import com.example.PayoEat.response.ApiResponse;
import com.example.PayoEat.service.restaurant.IRestaurantService;
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
            return ResponseEntity.ok(new ApiResponse("Restaurant found", restaurant));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/add-restaurant")
    @Operation(summary = "Add Restaurant", description = "Add Restaurant by Request")
    public ResponseEntity<ApiResponse> addRestaurant(@RequestBody AddRestaurantRequest request) {
        try {
            Restaurant newRestaurant = restaurantService.addRestaurant(request);
            return ResponseEntity.ok(new ApiResponse("Restaurant added successfully", newRestaurant));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error: " + e.getMessage(), null));
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Restaurants", description = "Getting all available restaurants")
    public ResponseEntity<ApiResponse> getAllRestaurants() {
        try {
            List<Restaurant> restaurants = restaurantService.getAllRestaurants();
            return ResponseEntity.ok(new ApiResponse("Found", restaurants));
        } catch (Exception e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }
    }
}
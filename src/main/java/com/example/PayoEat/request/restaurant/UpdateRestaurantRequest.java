package com.example.PayoEat.request.restaurant;

import lombok.Data;

@Data
public class UpdateRestaurantRequest {
    private String name;
    private Double rating;
    private String description;
}

package com.example.PayoEat.request;

import lombok.Data;

@Data
public class UpdateRestaurantRequest {
    private Long id;
    private String name;
    private Double rating;
    private String description;
}

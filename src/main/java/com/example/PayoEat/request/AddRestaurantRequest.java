package com.example.PayoEat.request;

import lombok.Data;

@Data
public class AddRestaurantRequest {
    private Long id;
    private String name;
    private Double rating;
    private String description;
}

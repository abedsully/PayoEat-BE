package com.example.PayoEat.dto;

import com.example.PayoEat.model.Image;
import com.example.PayoEat.model.Restaurant;
import lombok.Data;

@Data
public class MenuDto {
    private String menuCode;
    private String menuName;
    private String menuDetail;
    private Double menuPrice;
    private RestaurantDto restaurant;
    private ImageDto image;
}

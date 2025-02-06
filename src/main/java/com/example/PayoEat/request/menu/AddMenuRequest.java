package com.example.PayoEat.request.menu;

import lombok.Data;


import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class AddMenuRequest {
    @JsonProperty("menuName")
    private String menuName;

    @JsonProperty("menuDetail")
    private String menuDetail;

    @JsonProperty("menuPrice")
    private Double menuPrice;

    @JsonProperty("restaurantId")
    private Long restaurantId;

    public AddMenuRequest(String menuName, String menuDetail, double menuPrice, Long restaurantId) {
        this.menuName = menuName;
        this.menuDetail = menuDetail;
        this.menuPrice = menuPrice;
        this.restaurantId = restaurantId;
    }
}

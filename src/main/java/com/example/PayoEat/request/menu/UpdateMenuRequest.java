package com.example.PayoEat.request.menu;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMenuRequest {
    private String menuName;
    private String menuDetail;
    private Double menuPrice;
}

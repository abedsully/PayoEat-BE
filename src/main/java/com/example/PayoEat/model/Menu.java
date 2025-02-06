package com.example.PayoEat.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {
    @Id
    @Column(nullable = false, unique = true, length = 50)
    @Schema(description = "Unique identifier of the menu")
    private String menuCode;

    @Schema(description = "Name of the menu")
    private String menuName;

    @Schema(description = "Detail of the menu")
    private String menuDetail;

    @Schema(description = "Price of the menu")
    private Double menuPrice;

    @Schema(description = "Date in which the restaurant is created at")
    private LocalDateTime createdAt;

    @Schema(description = "The latest date the restaurant is updated at")
    private LocalDateTime updatedAt;

    @Schema(description = "Status of the menu")
    private Boolean isActive;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToOne(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Image menuImage;

    public Menu(String menuName, String menuDetail, double menuPrice) {
        this.menuName = menuName;
        this.menuDetail = menuDetail;
        this.menuPrice = menuPrice;
    }
}

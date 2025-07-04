package com.swiggy.menu_service.model;

import com.swiggy.menu_service.enums.MenuItemStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MenuItem {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private MenuItemStatus status; //available, unavailable

    private String foodType; //vegetarian, non-vegetarian, vegan, etc.

    private String category; //e.g., appetizer, main course, dessert

    private String imageUrl; //URL to the image of the menu item

    private Menu menu;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


}

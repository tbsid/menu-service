package com.swiggy.menu_service.model;

import com.swiggy.menu_service.enums.Cuisine;
import com.swiggy.menu_service.enums.RestaurantStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Restaurant {

    private Long id;

    private String name;

    private String address;

    private String phone;

    private String email;

    private String contactPerson;

    private String pincode;

    private String city;

    private String state;

    private String imageUrl;

    private String description;

    private Cuisine cuisine;

    private List<Menu> menus;

    private RestaurantStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

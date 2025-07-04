package com.swiggy.menu_service.model;

import com.swiggy.menu_service.enums.MenuStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Menu {

    private Long id;

    private String name;

    private Restaurant restaurant;

    private MenuStatus status;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}

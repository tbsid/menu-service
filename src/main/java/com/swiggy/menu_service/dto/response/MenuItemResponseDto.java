package com.swiggy.menu_service.dto.response;

import com.swiggy.menu_service.enums.Category;
import com.swiggy.menu_service.enums.FoodType;
import com.swiggy.menu_service.enums.MenuItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MenuItemResponseDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private MenuItemStatus status;
    private FoodType foodType;
    private Category category;

}

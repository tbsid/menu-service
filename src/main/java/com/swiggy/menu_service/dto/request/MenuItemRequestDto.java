package com.swiggy.menu_service.dto.request;

import com.swiggy.menu_service.enums.Category;
import com.swiggy.menu_service.enums.FoodType;
import com.swiggy.menu_service.enums.MenuItemStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Data
public class MenuItemRequestDto {

    @NotBlank
    @Size(min = 2, max = 50, message = "Item name must not exceed 50 characters")
    private String name;

    @NotNull(message = "Item price must be specified")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Item status must be provided")
    private MenuItemStatus status;

    @NotNull(message = "Food type must be specified")
    private FoodType foodType;

    @NotNull(message = "Category must be specified")
    private Category category;

    @Size(max = 500, message = "Item description must not exceed 500 characters")
    private String description;

    @URL(message = "Must be a valid URL")
    @Size(max = 255, message = "Image URL must not exceed 255 characters")
    private String imageUrl;

}

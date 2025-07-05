package com.swiggy.menu_service.dto.request;

import com.swiggy.menu_service.enums.MenuStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class MenuRequestDto {

    @NotBlank(message = "Menu name cannot be blank")
    @Size(min = 2, max = 50, message = "Menu name must be not exceed 50 characters")
    private String name;

    @NotNull
    private MenuStatus status;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotEmpty
    private List<@Valid MenuItemRequestDto> menuItems;

}

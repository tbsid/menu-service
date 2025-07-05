package com.swiggy.menu_service.dto.request;

import com.swiggy.menu_service.enums.Cuisine;
import com.swiggy.menu_service.enums.RestaurantStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Data
public class RestaurantRequestDto {

    @NotBlank(message = "Restaurant name is required")
    @Size(min = 1, max = 255, message = "Restaurant name must contain between 2 and 255 characters")
    private String name;

    @NotBlank(message = "Restaurant address is required")
    @Size(min = 1, max = 500, message = "Restaurant name must contain between 2 and 500 characters")
    private String address;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Mobile number should be valid (10-15 digits, may start with +)")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Contact person full name is required")
    @Size(min = 2, max = 100, message = "Contact person name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "First name can only contain letters and spaces")
    private String contactPerson;

    @NotBlank(message = "Pin code is required")
    @Size(max = 10, message = "Pin code cannot exceed 10 characters")
    private String pincode;

    @NotBlank(message = "Restaurant city is required")
    @Size(max = 25, message = "City name cannot exceed 25 characters")
    private String city;

    @NotBlank(message = "Restaurant state is required")
    @Size(max = 25, message = "State name cannot exceed 25 characters")
    private String state;

    @URL(message = "Must be a valid URL")
    @Size(max = 255, message = "Image URL must not exceed 255 characters")
    private String imageUrl;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull
    private Cuisine cuisine;

    @NotNull
    private RestaurantStatus status;

    @NotEmpty
    private List<@Valid MenuRequestDto> menus;

}

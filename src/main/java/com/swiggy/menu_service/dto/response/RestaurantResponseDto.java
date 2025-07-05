package com.swiggy.menu_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class RestaurantResponseDto {

    private Long id;
    private String name;
    private String message;
    private HttpStatus status;

}

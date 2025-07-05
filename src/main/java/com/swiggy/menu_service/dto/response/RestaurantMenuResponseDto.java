package com.swiggy.menu_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RestaurantMenuResponseDto {

    private Long restaurantId;
    private String restaurantName;
    private List<MenuResponseDto> menus;
    private int currentPage;
    private int pageSize;
    private long totalElements;
    private int totalPages;

}

package com.swiggy.menu_service.service;

import com.swiggy.menu_service.dto.request.RestaurantRequestDto;
import com.swiggy.menu_service.dto.response.RestaurantResponseDto;

public interface RestaurantService {

    RestaurantResponseDto createRestaurantWithMenu(RestaurantRequestDto restaurantRequestDto);

}

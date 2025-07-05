package com.swiggy.menu_service.service;

import com.swiggy.menu_service.dto.request.RestaurantRequestDto;
import com.swiggy.menu_service.dto.response.MenuResponseDto;
import com.swiggy.menu_service.dto.response.RestaurantMenuResponseDto;
import com.swiggy.menu_service.dto.response.RestaurantResponseDto;

public interface RestaurantService {

    RestaurantResponseDto createRestaurantWithMenu(RestaurantRequestDto restaurantRequestDto);

    RestaurantMenuResponseDto getMenuByRestaurantId(Long restaurantId);

    RestaurantResponseDto updateRestaurantMenu(Long restaurantId, RestaurantRequestDto request);

    void deleteRestaurant(Long restaurantId);

}

package com.swiggy.menu_service.service.impl;

import com.swiggy.menu_service.dto.request.RestaurantRequestDto;
import com.swiggy.menu_service.dto.response.MenuItemResponseDto;
import com.swiggy.menu_service.dto.response.MenuResponseDto;
import com.swiggy.menu_service.dto.response.RestaurantMenuResponseDto;
import com.swiggy.menu_service.dto.response.RestaurantResponseDto;
import com.swiggy.menu_service.enums.ValidationError;
import com.swiggy.menu_service.exception.ApplicationException;
import com.swiggy.menu_service.mapper.RestaurantMapper;
import com.swiggy.menu_service.model.Menu;
import com.swiggy.menu_service.model.MenuItem;
import com.swiggy.menu_service.model.Restaurant;
import com.swiggy.menu_service.repository.MenuItemRepository;
import com.swiggy.menu_service.repository.MenuRepository;
import com.swiggy.menu_service.repository.RestaurantRepository;
import com.swiggy.menu_service.service.RestaurantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private static final Logger log = LoggerFactory.getLogger(RestaurantServiceImpl.class);

    private final RestaurantMapper mapper;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    @Transactional
    public RestaurantResponseDto createRestaurantWithMenu(RestaurantRequestDto request) {
        log.info("Create restaurant with menu request received");
        try {
            Restaurant restaurant = mapper.createRestaurant(request);
            log.info("Restaurant object created successfully");

            List<Menu> menuList = mapper.createMenuList(request, restaurant);
            log.info("Menu list created successfully");

            restaurant.setMenus(menuList);
            restaurantRepository.save(restaurant);

            return new RestaurantResponseDto(restaurant.getId(), restaurant.getName(), "Restaurant created successfully", HttpStatus.CREATED);

        } catch (Exception e) {
            throw new RuntimeException("Failed to create restaurant and menu: " + e.getMessage(), e);
        }
    }

    @Override
    public RestaurantMenuResponseDto getMenuByRestaurantId(Long restaurantId) {
        log.info("Get menu by restaurant id received");

        //Step 1: Look for the menu in application cache
        //TODO: to be implemented

        //Step 2: If not found in application cache - Look for the menu in the distributed cache (redis)
        //TODO: to be implemented

        //Step 3: If not found in the redis cache - Look for the menu in the database
        log.info("Fetching menu by restaurant ID: {} from the database", restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ApplicationException(ValidationError.ENTITY_NOT_FOUND, "Restaurant not found with id: " + restaurantId));
        List<MenuResponseDto> menuResponseDto = mapper.createMenuResponseDto(restaurant.getMenus());
        log.info("Menu response dto list created successfully");
        return new RestaurantMenuResponseDto(restaurant.getId(), restaurant.getName(), menuResponseDto);
    }

    @Override
    public RestaurantResponseDto updateRestaurantMenu(Long restaurantId, RestaurantRequestDto request) {
        return null;
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {

    }
}

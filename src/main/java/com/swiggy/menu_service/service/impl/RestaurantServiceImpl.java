package com.swiggy.menu_service.service.impl;

import com.swiggy.menu_service.dto.request.MenuItemRequestDto;
import com.swiggy.menu_service.dto.request.MenuRequestDto;
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

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantMapper mapper;

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
        List<MenuResponseDto> menuResponseDto = mapper.createMenuResponseDtoList(restaurant.getMenus());
        log.info("Menu response dto list created successfully");
        return new RestaurantMenuResponseDto(restaurant.getId(), restaurant.getName(), menuResponseDto);
    }

    @Override
    public MenuResponseDto getMenuByRestaurantIdAndMenuId(Long restaurantId, Long menuId) {
        log.info("Get menu by restaurant id and menu id request received");
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new ApplicationException(ValidationError.ENTITY_NOT_FOUND, "Menu not found with id: " + menuId + " for restaurant id: " + restaurantId));
        log.info("Successfully fetched menu by restaurant id from the database");
        return mapper.mapMenuToResponseDto(menu);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        log.info("Deleting restaurant with id {} from the database", restaurantId);
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ApplicationException(ValidationError.ENTITY_NOT_FOUND, "Restaurant not found with id: " + restaurantId));
        restaurantRepository.delete(restaurant);
        log.info("Successfully deleted restaurant with id {} from the database", restaurantId);
    }

    @Override
    public RestaurantResponseDto updateRestaurantMenu(Long restaurantId, RestaurantRequestDto request) {
        log.info("Update restaurant menu request received for restaurant id {}", restaurantId);

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ApplicationException(ValidationError.ENTITY_NOT_FOUND, "Restaurant not found with id: " + restaurantId));

        List<Menu> updatedMenus = mapper.createMenuList(request, restaurant);
        List<Menu> existingMenus = restaurant.getMenus();
        existingMenus.clear();
        existingMenus.addAll(updatedMenus);
        log.info("Existing menus replaced with updated list for restaurant id {}", restaurantId);

        restaurantRepository.save(restaurant);
        log.info("Successfully updated menu for restaurant id {}", restaurantId);

        return new RestaurantResponseDto(restaurant.getId(), restaurant.getName(), "Restaurant menu updated successfully", HttpStatus.OK);
    }

    @Override
    public MenuResponseDto updateMenuById(Long restaurantId, Long menuId, MenuRequestDto request) {
        log.info("Update menu request received for restaurant id {} and menu id {}", restaurantId, menuId);

        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId)
                .orElseThrow(() -> new ApplicationException(ValidationError.ENTITY_NOT_FOUND,
                        "Menu not found with id: " + menuId + " for restaurant id: " + restaurantId));

        mapper.updateMenuFromRequest(menu, request);

        menuRepository.save(menu);
        log.info("Successfully updated menu with id {}", menuId);

        return mapper.mapMenuToResponseDto(menu);
    }

    @Override
    public MenuItemResponseDto updateMenuItemById(Long menuItemId, MenuItemRequestDto request) {
        log.info("Update menu item request received for menu item id {}", menuItemId);

        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ApplicationException(ValidationError.ENTITY_NOT_FOUND,
                        "Menu item not found with id: " + menuItemId));

        mapper.updateMenuItemFromRequest(item, request);
        menuItemRepository.save(item);
        log.info("Successfully updated menu item with id {}", menuItemId);

        return new MenuItemResponseDto(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getStatus(),
                item.getFoodType(),
                item.getCategory()
        );
    }

}

package com.swiggy.menu_service.mapper;

import com.swiggy.menu_service.dto.request.MenuItemRequestDto;
import com.swiggy.menu_service.dto.request.MenuRequestDto;
import com.swiggy.menu_service.dto.request.RestaurantRequestDto;
import com.swiggy.menu_service.dto.response.MenuItemResponseDto;
import com.swiggy.menu_service.dto.response.MenuResponseDto;
import com.swiggy.menu_service.model.Menu;
import com.swiggy.menu_service.model.MenuItem;
import com.swiggy.menu_service.model.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantMapper {

    private static Logger log = LoggerFactory.getLogger(RestaurantMapper.class);

    public Restaurant createRestaurant(RestaurantRequestDto request) {
        log.info("Creating restaurant from restaurant request dto");
        return Restaurant.builder()
                .name(request.getName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .contactPerson(request.getContactPerson())
                .pincode(request.getPincode())
                .city(request.getCity())
                .state(request.getState())
                .imageUrl(request.getImageUrl())
                .description(request.getDescription())
                .cuisine(request.getCuisine())
                .status(request.getStatus())
                .build();
    }

    public List<Menu> createMenuList(RestaurantRequestDto request, Restaurant restaurant) {
        log.info("Creating menu list from restaurant request dto");
        List<Menu> menus = new ArrayList<>();
        List<MenuRequestDto> menuRequestDtos = request.getMenus();
        for(MenuRequestDto menuRequestDto : menuRequestDtos) {
            Menu menu = Menu.builder()
                    .name(menuRequestDto.getName())
                    .description(menuRequestDto.getDescription())
                    .status(menuRequestDto.getStatus())
                    .restaurant(restaurant)
                    .build();
            menu.setMenuItems(createMenuItemList(menuRequestDto, menu));
            menus.add(menu);
        }
        log.info("Successfully created menu list from restaurant request dto");
        return menus;
    }

    private List<MenuItem> createMenuItemList(MenuRequestDto menuRequestDto, Menu menu) {
        List<MenuItem> itemList = new ArrayList<>();
        log.info("Creating menu item list from Menu request dto");
        for(MenuItemRequestDto itemRequestDto : menuRequestDto.getMenuItems()) {
            MenuItem item = MenuItem.builder()
                    .name(itemRequestDto.getName())
                    .description(itemRequestDto.getDescription())
                    .price(itemRequestDto.getPrice())
                    .status(itemRequestDto.getStatus())
                    .foodType(itemRequestDto.getFoodType())
                    .category(itemRequestDto.getCategory())
                    .imageUrl(itemRequestDto.getImageUrl())
                    .menu(menu)
                    .restaurant(menu.getRestaurant())  // Add restaurant reference for denormalization
                    .build();
            itemList.add(item);
        }
        log.info("Successfully created menu item list from Menu request dto");
        return itemList;
    }

    public List<MenuResponseDto> createMenuResponseDtoList(List<Menu> menuList) {
        log.info("Creating menu response dto from Menu list");
        return menuList.stream()
                .map(this::mapMenuToResponseDto)
                .toList();
    }

    public MenuResponseDto mapMenuToResponseDto(Menu menu) {
        log.info("Mapping menu to restaurant response dto");
        return new MenuResponseDto(
                menu.getId(),
                menu.getName(),
                menu.getStatus(),
                menu.getDescription(),
                menu.getMenuItems().stream()
                        .map(this::mapMenuItemToResponseDto)
                        .toList()
        );
    }

    private MenuItemResponseDto mapMenuItemToResponseDto(MenuItem item) {
        log.info("Mapping menu item to menu item response dto");
        return new MenuItemResponseDto(
                item.getId(),
                item.getName(),
                item.getPrice(),
                item.getStatus(),
                item.getFoodType(),
                item.getCategory()
        );
    }

    public void updateMenuFromRequest(Menu menu, MenuRequestDto request) {
        log.info("Mapping MenuRequestDto to Menu entity");
        menu.setName(request.getName());
        menu.setDescription(request.getDescription());
        menu.setStatus(request.getStatus());

        menu.getMenuItems().clear();
        List<MenuItem> updatedItems = request.getMenuItems().stream().map(itemReq -> MenuItem.builder()
                .name(itemReq.getName())
                .description(itemReq.getDescription())
                .price(itemReq.getPrice())
                .status(itemReq.getStatus())
                .foodType(itemReq.getFoodType())
                .category(itemReq.getCategory())
                .imageUrl(itemReq.getImageUrl())
                .menu(menu)
                .restaurant(menu.getRestaurant())  // Add restaurant reference for denormalization
                .build()).toList();
        menu.setMenuItems(updatedItems);
    }

    public void updateMenuItemFromRequest(MenuItem item, MenuItemRequestDto request) {
        log.info("Mapping MenuItemRequestDto to MenuItem entity");
        item.setName(request.getName());
        item.setDescription(request.getDescription());
        item.setPrice(request.getPrice());
        item.setStatus(request.getStatus());
        item.setFoodType(request.getFoodType());
        item.setCategory(request.getCategory());
        item.setImageUrl(request.getImageUrl());
    }
}

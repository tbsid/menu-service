package com.swiggy.menu_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.menu_service.dto.request.MenuItemRequestDto;
import com.swiggy.menu_service.dto.request.MenuRequestDto;
import com.swiggy.menu_service.dto.request.RestaurantRequestDto;
import com.swiggy.menu_service.dto.response.MenuItemResponseDto;
import com.swiggy.menu_service.dto.response.MenuResponseDto;
import com.swiggy.menu_service.dto.response.RestaurantMenuResponseDto;
import com.swiggy.menu_service.dto.response.RestaurantResponseDto;
import com.swiggy.menu_service.enums.*;
import com.swiggy.menu_service.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestaurantController.class)
@AutoConfigureMockMvc
class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RestaurantService restaurantService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testCreateRestaurantWithMenu_ReturnsCreated() throws Exception {
        RestaurantRequestDto request = getSampleRestaurantRequest();
        RestaurantResponseDto response = new RestaurantResponseDto(1L, "Hotel ABC", "Restaurant created successfully", HttpStatus.CREATED);

        when(restaurantService.createRestaurantWithMenu(any(RestaurantRequestDto.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/restaurant/create-with-menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Hotel ABC"))
                .andExpect(jsonPath("$.message").value("Restaurant created successfully"));
    }

    @Test
    void testGetRestaurantMenu_ReturnsMenuResponse() throws Exception {
        Long restaurantId = 1L;
        int page = 0, size = 10;
        RestaurantMenuResponseDto response = new RestaurantMenuResponseDto(
                restaurantId, "Hotel ABC", List.of(getSampleMenuResponse()), page, size, 1L, 1
        );

        when(restaurantService.getMenuByRestaurantId(restaurantId, page, size)).thenReturn(response);

        mockMvc.perform(get("/api/v1/restaurant/{id}/menu", restaurantId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurantId").value(restaurantId))
                .andExpect(jsonPath("$.restaurantName").value("Hotel ABC"))
                .andExpect(jsonPath("$.menus[0].name").value("Lunch"));
    }

    // Sample Data Helpers

    private RestaurantRequestDto getSampleRestaurantRequest() {
        MenuItemRequestDto item = new MenuItemRequestDto();
        item.setName("Paneer Tikka");
        item.setPrice(BigDecimal.valueOf(250));
        item.setStatus(MenuItemStatus.AVAILABLE);
        item.setFoodType(FoodType.VEG);
        item.setCategory(Category.STARTER);

        MenuRequestDto menu = new MenuRequestDto();
        menu.setName("Lunch");
        menu.setStatus(MenuStatus.ACTIVE);
        menu.setMenuItems(List.of(item));

        RestaurantRequestDto request = new RestaurantRequestDto();
        request.setName("Hotel ABC");
        request.setAddress("FC road");
        request.setPhone("9988112233");
        request.setEmail("jacky@test.com");
        request.setContactPerson("Jacky Chan");
        request.setPincode("411058");
        request.setCity("Pune");
        request.setState("MH");
        request.setCuisine(Cuisine.INDIAN);
        request.setStatus(RestaurantStatus.ACTIVE);
        request.setMenus(List.of(menu));

        return request;
    }

    private MenuResponseDto getSampleMenuResponse() {
        MenuItemResponseDto itemDto = new MenuItemResponseDto(
                100L,
                "Paneer Tikka",
                BigDecimal.valueOf(199),
                MenuItemStatus.AVAILABLE,
                FoodType.VEG,
                Category.STARTER
        );

        return new MenuResponseDto(
                10L,
                "Lunch",
                MenuStatus.ACTIVE,
                "Lunch menu",
                List.of(itemDto)
        );
    }
}

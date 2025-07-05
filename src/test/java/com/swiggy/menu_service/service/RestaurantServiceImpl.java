package com.swiggy.menu_service.service;

import com.swiggy.menu_service.dto.request.MenuItemRequestDto;
import com.swiggy.menu_service.dto.request.MenuRequestDto;
import com.swiggy.menu_service.dto.request.RestaurantRequestDto;
import com.swiggy.menu_service.dto.response.MenuItemResponseDto;
import com.swiggy.menu_service.dto.response.MenuResponseDto;
import com.swiggy.menu_service.dto.response.RestaurantMenuResponseDto;
import com.swiggy.menu_service.dto.response.RestaurantResponseDto;
import com.swiggy.menu_service.enums.*;
import com.swiggy.menu_service.exception.ApplicationException;
import com.swiggy.menu_service.mapper.RestaurantMapper;
import com.swiggy.menu_service.model.Menu;
import com.swiggy.menu_service.model.MenuItem;
import com.swiggy.menu_service.model.Restaurant;
import com.swiggy.menu_service.repository.MenuItemRepository;
import com.swiggy.menu_service.repository.MenuRepository;
import com.swiggy.menu_service.repository.RestaurantRepository;
import com.swiggy.menu_service.service.impl.RestaurantServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantServiceImpl restaurantService;

    @Test
    void testCreateRestaurantWithMenu_Success() {
        // Arrange
        RestaurantRequestDto requestDto = getSampleRestaurantRequest();
        Restaurant mappedRestaurant = getSampleRestaurantEntity();
        mappedRestaurant.setId(1L); // simulate DB-generated ID

        when(restaurantMapper.createRestaurant(requestDto)).thenReturn(mappedRestaurant);
        when(restaurantMapper.createMenuList(requestDto, mappedRestaurant)).thenReturn(mappedRestaurant.getMenus());
        when(restaurantRepository.save(mappedRestaurant)).thenReturn(mappedRestaurant);

        // Act
        RestaurantResponseDto response = restaurantService.createRestaurantWithMenu(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Hotel ABC", response.getName());
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals("Restaurant created successfully", response.getMessage());

        verify(restaurantMapper).createRestaurant(requestDto);
        verify(restaurantMapper).createMenuList(requestDto, mappedRestaurant);
        verify(restaurantRepository).save(mappedRestaurant);
    }

    // Sample data setup
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
        request.setEmail("jack@teest.com");
        request.setContactPerson("Jacky Chan");
        request.setPincode("411058");
        request.setCity("Pune");
        request.setState("MH");
        request.setCuisine(Cuisine.INDIAN);
        request.setStatus(RestaurantStatus.ACTIVE);
        request.setMenus(List.of(menu));

        return request;
    }

    private Restaurant getSampleRestaurantEntity() {
        return Restaurant.builder()
                .name("Hotel ABC")
                .address("FC road")
                .phone("9988112233")
                .email("jacky@test.com")
                .contactPerson("Jacky Chan")
                .pincode("411058")
                .city("Pune")
                .state("MH")
                .cuisine(Cuisine.INDIAN)
                .status(RestaurantStatus.ACTIVE)
                .menus(new ArrayList<>())
                .build();
    }

    @Test
    void testGetMenuByRestaurantId_ReturnsPaginatedMenus() {
        Long restaurantId = 1L;
        int page = 0;
        int size = 1;

        Restaurant restaurant = getSampleRestaurantEntity();
        restaurant.setId(restaurantId);

        Menu menu = getSampleMenuEntity();
        menu.setRestaurant(restaurant);
        menu.setId(10L);

        Page<Menu> menuPage = new PageImpl<>(List.of(menu));

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuRepository.findByRestaurantId(eq(restaurantId), any(PageRequest.class))).thenReturn(menuPage);
        when(restaurantMapper.createMenuResponseDtoList(menuPage.getContent())).thenReturn(List.of(getSampleMenuResponse()));

        RestaurantMenuResponseDto response = restaurantService.getMenuByRestaurantId(restaurantId, page, size);

        assertEquals(restaurantId, response.getRestaurantId());
        assertEquals("Hotel ABC", response.getRestaurantName());
        assertEquals(1, response.getMenus().size());
    }

    private MenuResponseDto getSampleMenuResponse() {
        MenuItemResponseDto itemDto = new MenuItemResponseDto(
                100L,
                "Paneer Tikka",
                BigDecimal.valueOf(250),
                MenuItemStatus.AVAILABLE,
                FoodType.VEG,
                Category.STARTER
        );

        return new MenuResponseDto(
                10L,
                "Lunch",
                MenuStatus.ACTIVE,
                "Midday meal menu",
                List.of(itemDto)
        );
    }



    @Test
    void testDeleteRestaurant_Success() {
        Long restaurantId = 1L;
        Restaurant restaurant = getSampleRestaurantEntity();
        restaurant.setId(restaurantId);

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));

        restaurantService.deleteRestaurant(restaurantId);

        verify(restaurantRepository).delete(restaurant);
    }

    @Test
    void testDeleteRestaurant_NotFound_ThrowsException() {
        Long restaurantId = 99L;
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.empty());

        assertThrows(ApplicationException.class, () -> restaurantService.deleteRestaurant(restaurantId));
    }


    @Test
    void testUpdateRestaurantMenu_Success() {
        Long restaurantId = 1L;
        RestaurantRequestDto request = getSampleRestaurantRequest();
        Restaurant existing = getSampleRestaurantEntity();
        existing.setId(restaurantId);

        List<Menu> updatedMenus = List.of(getSampleMenuEntity());

        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(existing));
        when(restaurantMapper.createMenuList(request, existing)).thenReturn(updatedMenus);
        when(restaurantRepository.save(existing)).thenReturn(existing);

        RestaurantResponseDto response = restaurantService.updateRestaurantMenu(restaurantId, request);

        assertEquals("Restaurant menu updated successfully", response.getMessage());
        verify(restaurantRepository).save(existing);
    }

    private Menu getSampleMenuEntity() {
        MenuItem item = MenuItem.builder()
                .id(100L)
                .name("Paneer Tikka")
                .description("Grilled paneer cubes with spices")
                .price(BigDecimal.valueOf(250))
                .status(MenuItemStatus.AVAILABLE)
                .foodType(FoodType.VEG)
                .category(Category.STARTER)
                .imageUrl("https://example.com/images/paneer-tikka.jpg")
                .build();

        Menu menu = Menu.builder()
                .id(10L)
                .name("Lunch")
                .description("Midday meal menu")
                .status(MenuStatus.ACTIVE)
                .menuItems(new ArrayList<>(List.of(item)))
                .build();

        item.setMenu(menu); // Set the back-reference

        return menu;
    }

}


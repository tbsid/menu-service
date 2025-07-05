package com.swiggy.menu_service.controller;

import com.swiggy.menu_service.dto.request.RestaurantRequestDto;
import com.swiggy.menu_service.dto.response.RestaurantMenuResponseDto;
import com.swiggy.menu_service.dto.response.RestaurantResponseDto;
import com.swiggy.menu_service.service.RestaurantService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/restaurant")
public class RestaurantController {

    private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        log.info("Hello World endpoint called");
        return "Hello World - Welcome to our Restaurant. We're now live on Swiggy!";
    }

    @PostMapping("/create-with-menu")
    public ResponseEntity<RestaurantResponseDto> createRestaurantWithMenu(@RequestBody @Valid RestaurantRequestDto request) {
        log.info("Request received in create restaurant with menu API");
        RestaurantResponseDto response = restaurantService.createRestaurantWithMenu(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<RestaurantMenuResponseDto> getRestaurantMenu(@PathVariable Long id,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size) {
        RestaurantMenuResponseDto response = restaurantService.getMenuByRestaurantId(id, page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/menu")
    public ResponseEntity<RestaurantResponseDto> updateRestaurantMenu(@PathVariable Long id, @RequestBody @Valid RestaurantRequestDto request) {
        RestaurantResponseDto response = restaurantService.updateRestaurantMenu(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

}

package com.swiggy.menu_service.repository;

import com.swiggy.menu_service.model.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("SELECT mi FROM MenuItem mi WHERE mi.restaurant.id = :restaurantId AND mi.menu.status = 'ACTIVE'")
    Page<MenuItem> findByRestaurantIdAndActiveMenu(@Param("restaurantId") Long restaurantId, Pageable pageable);
}

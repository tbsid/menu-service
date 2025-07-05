package com.swiggy.menu_service.repository;

import com.swiggy.menu_service.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByIdAndRestaurantId(Long id, Long restaurantId);

}

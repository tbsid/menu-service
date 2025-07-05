package com.swiggy.menu_service.dto.response;

import com.swiggy.menu_service.enums.MenuStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MenuResponseDto {

    private Long id;
    private String name;
    private MenuStatus status;
    private String description;
    private List<MenuItemResponseDto> items;

}

package com.swiggy.menu_service.config.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@Slf4j
public class ApiGroupProperties {

    private String name;
    private List<String> pathsToMatch;
    private List<String> packagesToScan;

}

package com.swiggy.menu_service.config.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "documentation.openapi")
public class OpenApiProperties {

    private String title = "API Documentation";
    private String description = "API Documentation";
    private String version = "1.0.0";
    private String contactName;
    private String contactEmail;
    private String contactUrl;
    private String licenseName;
    private String licenseUrl;
    private ApiGroupProperties group;
}

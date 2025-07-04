package com.swiggy.menu_service.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.swiggy.menu_service.config.model.OpenApiProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OpenApiProperties.class)
@ConditionalOnProperty(prefix = "documentation.openapi", name = "title")
public class OpenApiConfig {

    private final OpenApiProperties openApiProperties;

    public OpenApiConfig(OpenApiProperties openApiProperties) {
        this.openApiProperties = openApiProperties;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title(openApiProperties.getTitle())
                        .version(openApiProperties.getVersion())
                        .description(openApiProperties.getDescription()));

        // Add contact information if provided
        if (openApiProperties.getContactName() != null ||
                openApiProperties.getContactEmail() != null ||
                openApiProperties.getContactUrl() != null) {

            Contact contact = new Contact();
            if (openApiProperties.getContactName() != null) {
                contact.name(openApiProperties.getContactName());
            }
            if (openApiProperties.getContactEmail() != null) {
                contact.email(openApiProperties.getContactEmail());
            }
            if (openApiProperties.getContactUrl() != null) {
                contact.url(openApiProperties.getContactUrl());
            }
            openAPI.getInfo().contact(contact);
        }

        // Add license information if provided
        if (openApiProperties.getLicenseName() != null || openApiProperties.getLicenseUrl() != null) {
            License license = new License();
            if (openApiProperties.getLicenseName() != null) {
                license.name(openApiProperties.getLicenseName());
            }
            if (openApiProperties.getLicenseUrl() != null) {
                license.url(openApiProperties.getLicenseUrl());
            }
            openAPI.getInfo().license(license);
        }

        return openAPI;
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group(openApiProperties.getGroup().getName())
                .pathsToMatch(openApiProperties.getGroup().getPathsToMatch().toArray(new String[0]))
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // Register JavaTimeModule to support Java 8 date/time types
        mapper.registerModule(new JavaTimeModule());
        // Disable timestamps in favor of ISO-8601 date/time representation
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Register Hibernate module for lazy loading support
        mapper.registerModule(new Hibernate6Module()
                .configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, false)
                .configure(Hibernate6Module.Feature.USE_TRANSIENT_ANNOTATION, true)
        );

        return mapper;
    }

}

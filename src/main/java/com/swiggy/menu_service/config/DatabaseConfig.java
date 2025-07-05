package com.swiggy.menu_service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.hikari.connectionTimeout:30000}")
    private long connectionTimeout;

    @Value("${spring.hikari.idleTimeout:600000}")
    private long idleTimeout;

    @Value("${spring.hikari.maxLifetime:1800000}")
    private long maxLifetime;

    @Value("${spring.hikari.minimumIdle:10}")
    private int minimumIdle;

    @Value("${spring.hikari.maximumPoolSize:10}")
    private int maximumPoolSize;

    @Value("${spring.hikari.autoCommit:true}")
    private boolean autoCommit;

    private static final Logger log = LoggerFactory.getLogger(DatabaseConfig.class);

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);

        // HikariCP specific configurations
        config.setConnectionTimeout(connectionTimeout);
        config.setIdleTimeout(idleTimeout);
        config.setMaxLifetime(maxLifetime);
        config.setMinimumIdle(minimumIdle);
        config.setMaximumPoolSize(maximumPoolSize);
        config.setAutoCommit(autoCommit);

        log.info("Configuring HikariCP with maximumPoolSize: {}, minimumIdle: {}", maximumPoolSize, minimumIdle);

        return new HikariDataSource(config);
    }
}

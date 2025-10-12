package ru.academy.course.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class PostgresqlProperties {

    @Value("${app.settings.postgresql.jdbcUrl}")
    private String jdbcUrl;

    @Value("${app.settings.postgresql.driverClassName}")
    private String driverClassName;

    @Value("${app.settings.postgresql.username}")
    private String username;

    @Value("${app.settings.postgresql.password}")
    private String password;

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

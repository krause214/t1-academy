package ru.academy.course.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration {

    @Bean
    public Connection connection(PostgresqlProperties props) throws SQLException {
        HikariConfig config = new HikariConfig();
        HikariDataSource dataSource;

        config.setJdbcUrl(props.getJdbcUrl());
        config.setDriverClassName(props.getDriverClassName());
        config.setUsername(props.getUsername());
        config.setPassword(props.getPassword());
        dataSource = new HikariDataSource(config);

        return dataSource.getConnection();
    }
}

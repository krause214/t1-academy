package ru.academy.course.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import ru.academy.course.spirngcontext.UserDao;
import ru.academy.course.spirngcontext.UserService;

import java.sql.Connection;
import java.sql.SQLException;

public class Configuration {

    @Bean
    public Connection connection() throws SQLException {
        HikariConfig config = new HikariConfig();
        HikariDataSource dataSource;

        config.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
        config.setDriverClassName("org.postgresql.Driver");
        config.setUsername("postgres");
        config.setPassword("password");
        dataSource = new HikariDataSource(config);

        return dataSource.getConnection();
    }

    @Bean
    public UserDao userDao(Connection connection) {
        return new UserDao(connection);
    }

    @Bean
    public UserService userService(UserDao userDao) {
        return new UserService(userDao);
    }

}

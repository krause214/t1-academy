package ru.academy.course.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.academy.course.springboot.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByUsername(String username);
    void deleteByUsernameIn(List<String> usernameList);
}

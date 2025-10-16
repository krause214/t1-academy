package ru.academy.course.springboot.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.academy.course.springboot.entity.User;
import ru.academy.course.springboot.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        log.info("Создание объекта user {}", user);
        User created = null;
        try {
            created = userRepository.save(user);
            log.info("Обект user {} успешно создан.", created);
            return created;
        } catch (Exception e) {
            log.warn("Создать объект user {} не удалось!", user);
            log.warn(e.getMessage());
            return created;
        }
    }

    @Transactional
    public List<User> createUsers(List<User> userList) {
        log.info("Создание обхектов user {}", userList);
        System.out.println();
        List<User> created = new ArrayList<>();
        try {
            created = userRepository.saveAll(userList);
            log.info("INFO Создание объектов user завершено {}", created);
            return created;
        } catch (Exception e) {
            System.out.println("WARN Создать объекты user " + userList + " не удалось!");
            System.out.println(e.getMessage());
            return created;
        }
    }

    @Transactional
    public void updateUser(User user) {
        if (user == null || user.getId() == null) {
            System.out.println("WARN Передан некорректный пользователь для обновления " + user);
            return;
        }
        System.out.println("INFO Обновление объекта user " + user + " по идентификатору " + user.getId());
        try {
            userRepository.save(user);
            System.out.println("INFO Пользватель " + user + " успешно обновлен!");
        } catch (Exception e) {
            System.out.println("WARN Обновить пользвателя " + user + " не удалось!");
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public User getUser(Long id) {
        System.out.println("INFO Получение пользователя по идентификатору " + id);
        User user = null;
        try {
            user = userRepository.findById(id).orElse(null);
            return user;
        } catch (Exception e) {
            System.out.println("WARN Получить пользвателя по идентификатору " + id + " не удалось!");
            System.out.println(e.getMessage());
            return user;
        }
    }

    @Transactional
    public List<User> getUsers(List<Long> idList) {
        System.out.println("INFO Обновление объектов user по идентификаторам " + idList);
        List<User> userList = new ArrayList<>();
        for (Long id : idList) {
            userList.add(getUser(id));
        }
        System.out.println("INFO Получение объектов user завершено " + userList);
        return userList;
    }

    @Transactional
    public void deleteUser(Long id) {
        System.out.println("INFO Удаление объекта user по идентификатору " + id);
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("WARN Удаление объекта user по идентификатору " + id + " не удалось!");
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public void deleteUsers(List<String> usernameList) {
        System.out.println("INFO Удаление объектов user по именам пользователей " + usernameList);
        try {
            userRepository.deleteByUsernameIn(usernameList);
        } catch (Exception e) {
            System.out.println("WARN Удаление объекта user по именам пользователей " + usernameList + " не удалось!");
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public void deleteUser(String username) {
        System.out.println("INFO Удаление объекта user по имени пользователя " + username);
        try {
            userRepository.deleteByUsername(username);
        } catch (Exception e) {
            System.out.println("WARN Удаление объекта user по имени пользователя " + username + " не удалось!");
            System.out.println(e.getMessage());
        }
    }

    @Transactional
    public List<User> getAllUsers() {
        System.out.println("INFO Получение всех объектов user");
        List<User> userList = new ArrayList<>();
        try {
            userList = userRepository.findAll();
            System.out.println("INFO Получение всех объектов user завершено " + userList);
            return userList;
        } catch (Exception e) {
            System.out.println("WARN Получение всех объектов user не удалось!");
            System.out.println(e.getMessage());
        }
        return userList;
    }
}

package ru.academy.course.spirngcontext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User createUser(User user) {
        System.out.println("INFO Создание объекта user " + user);
        User created = null;
        try {
            created = userDao.createUser(user);
            System.out.println("INFO Обект user " + created + " успешно создан.");
            return created;
        } catch (Exception e) {
            System.out.println("WARN Создать объект user " + user + " не удалось!");
            System.out.println(e.getMessage());
            return created;
        }
    }

    public List<User> createUsers(List<User> userList) {
        System.out.println("INFO Создание обхектов user " + userList);
        List<User> createdUserList = new ArrayList<>();
        for (User user: userList) {
            createdUserList.add(createUser(user));
        }
        System.out.println("INFO Создание объектов user завершено " + createdUserList);
        return createdUserList;
    }

    public void updateUser(User user) {
        if (user == null || user.getId() == null) {
            System.out.println("WARN Передан некорректный пользователь для обновления " + user);
            return;
        }
        System.out.println("INFO Обновление объекта user " + user + " по идентификатору " + user.getId());
        try {
            userDao.updateUser(user);
            System.out.println("INFO Пользватель " + user + " успешно обновлен!");
        } catch (Exception e) {
            System.out.println("WARN Обновить пользвателя " + user + " не удалось!");
            System.out.println(e.getMessage());
        }
    }

    public User getUser(Long id) {
        System.out.println("INFO Получение пользователя по идентификатору " + id);
        User user = null;
        try {
            user = userDao.readUser(id);
            return user;
        } catch (Exception e) {
            System.out.println("WARN Получить пользвателя по идентификатору " + id + " не удалось!");
            System.out.println(e.getMessage());
            return user;
        }
    }

    public List<User> getUsers(List<Long> idList) {
        System.out.println("INFO Обновление объектов user по идентификаторам " + idList);
        List<User> userList = new ArrayList<>();
        for (Long id : idList) {
            userList.add(getUser(id));
        }
        System.out.println("INFO Получение объектов user завершено " + userList);
        return userList;
    }

    public void deleteUser(Long id) {
        System.out.println("INFO Удаление объекта user по идентификатору " + id);
        try {
            userDao.deleteUser(id);
        } catch (Exception e) {
            System.out.println("WARN Удаление объекта user по идентификатору " + id + " не удалось!");
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        System.out.println("INFO Получение всех объектов user");
        List<User> userList = new ArrayList<>();
        try {
            userList = userDao.getAllUsers();
            System.out.println("INFO Получение всех объектов user завершено " + userList);
            return userList;
        } catch (Exception e) {
            System.out.println("WARN Получение всех объектов user не удалось!");
            System.out.println(e.getMessage());
        }
        return userList;
    }
}

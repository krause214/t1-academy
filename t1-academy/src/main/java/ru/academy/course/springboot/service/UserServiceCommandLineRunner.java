package ru.academy.course.springboot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.academy.course.springboot.entity.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceCommandLineRunner implements CommandLineRunner {

    private final UserService userService;

    @Override
    public void run(String... args) {
        userService.deleteUser("some_username");
        User user = userService.createUser(new User("some_username"));
        userService.deleteUser(user.getId());

        userService.deleteUser("other_username_1");
        User otherUser = new User("other_username_0");
        userService.createUser(otherUser);
        otherUser.setUsername("other_username_1");
        userService.updateUser(otherUser);

        List<User> userToCreate = List.of(
                new User("Pupa"),
                new User("Lupa"),
                new User("abvgd"),
                new User("PUPUPU"),
                new User("other_username")
        );

        userService.deleteUsers(userToCreate.stream()
                .map(User::getUsername)
                .toList());

        List<User> createdUser = userService.createUsers(userToCreate);

        List<Long> idListToGet = List.of(
                createdUser.get(0).getId(),
                createdUser.get(1).getId()
        );

        List<User> getUserList = userService.getUsers(idListToGet);

        User userToDelete = createdUser.get(0);
        userService.deleteUser(userToDelete.getId());
        userService.deleteUser(userToDelete.getId());

        List<User> getAllUsers = userService.getAllUsers();
    }
}

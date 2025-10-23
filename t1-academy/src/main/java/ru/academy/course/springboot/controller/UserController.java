package ru.academy.course.springboot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.academy.course.springboot.model.ListProductResponseDto;
import ru.academy.course.springboot.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}/products")
    public ResponseEntity<ListProductResponseDto> getUserProducts(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserProducts(userId));
    }
}

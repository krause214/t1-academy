package ru.academy.course.springboot.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.academy.course.springboot.controller.ProductController;
import ru.academy.course.springboot.exception.CommonError;

@Slf4j
@RestControllerAdvice(assignableTypes = ProductController.class)
public class ProductControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonError> handleError(Exception e) {
        log.error("Ошибка - {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CommonError("UNKNOWN_ERROR", e.getMessage()));
    }
}

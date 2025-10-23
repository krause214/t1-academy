package ru.academy.pay.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.academy.pay.controller.CorePayController;
import ru.academy.pay.exception.CommonError;

@Slf4j
@RestControllerAdvice(assignableTypes = CorePayController.class)
public class CorePayControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonError> handleError(Exception e) {
        log.error("Ошибка - {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CommonError("UNKNOWN_ERROR", e.getMessage()));
    }
}

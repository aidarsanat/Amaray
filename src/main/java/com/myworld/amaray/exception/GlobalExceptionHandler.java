// src/main/java/com/myworld/amaray/exception/GlobalExceptionHandler.java
package com.myworld.amaray.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
//@RestControllerAdvice — это Spring аннотация, она автоматически сканируется при запуске так же как @RestController, @Service, @Repository. Просто положи файл в любое место внутри пакета com.myworld.amaray — Spring найдёт сам.
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {

        String message = ex.getMessage();
        HttpStatus status;

        if (message != null && (message.contains("не найден") || message.contains("не найдена"))) {
            status = HttpStatus.NOT_FOUND; // 404
        } else if (message != null && message.contains("уже существует")) {
            status = HttpStatus.CONFLICT; // 409
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        }

        return ResponseEntity.status(status).body(Map.of(
                "error", message != null ? message : "Неизвестная ошибка",
                "status", status.value(),
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}

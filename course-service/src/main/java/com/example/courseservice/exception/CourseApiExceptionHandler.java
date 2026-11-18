package com.example.courseservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatusCode;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class CourseApiExceptionHandler {
    public record  ApiError(OffsetDateTime timestamp, int status, String message, String path) {

    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> status(ResponseStatusException e, HttpServletRequest req) {
        return reply(e.getStatusCode(), e.getReason(), req);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException e, HttpServletRequest req) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst().map(f -> f.getField() + ":" + f.getDefaultMessage()).orElse("Du lieu khong hop le");
        return reply(HttpStatus.BAD_REQUEST, message, req);
    }

    private ResponseEntity<ApiError> reply(HttpStatusCode code, String message, HttpServletRequest request) {
        return ResponseEntity.status(code).body(new ApiError(
                OffsetDateTime.now(), code.value(), message, request.getRequestURI()
        ));
    }
}

package com.example.studentservice.exception;

import com.example.studentservice.dto.StudentResponse;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class StudentApiExceptionHandler extends RuntimeException {
    public record ApiError(OffsetDateTime timestamp, int status, String message, String path) {}

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ApiError> feign(FeignException e, HttpServletRequest req) {
        HttpStatus status = switch (e.status()) {
            case 404 -> HttpStatus.NOT_FOUND;
            case 409 -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_GATEWAY;
        };
        String message = status == HttpStatus.NOT_FOUND
                ? "Khoa hoc khong ton tai "
                : status == HttpStatus.CONFLICT ? "Khoa hoc da het cho"
                : "Khong goi duoc course service";
        return reply(status, message, req);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> badRequest(MethodArgumentNotValidException e, HttpServletRequest req) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst().map(f -> f.getField() + ":" + f.getDefaultMessage()).orElse("Du lieu khong hop le");
        return reply(HttpStatus.BAD_REQUEST, message, req);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> constraint(DataIntegrityViolationException e, HttpServletRequest req) {
        return reply(HttpStatus.CONFLICT, "Du lieu trung hoac khong hop le", req);
    }

    private ResponseEntity<ApiError> reply(HttpStatusCode code, String message, HttpServletRequest request) {
        return ResponseEntity.status(code).body(new ApiError(
                OffsetDateTime.now(), code.value(), message, request.getRequestURI()
        ));
    }


}

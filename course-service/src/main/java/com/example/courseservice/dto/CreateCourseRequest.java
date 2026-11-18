package com.example.courseservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateCourseRequest(
        @NotBlank String courseName,
        @NotNull @Min(0) Integer capacity,
        @NotNull @Positive Integer credits
) {
}

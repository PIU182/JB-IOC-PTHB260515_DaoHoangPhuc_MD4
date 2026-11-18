package com.example.studentservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EnrollRequest(@NotNull @Positive Long studentId,
                            @NotNull @Positive Long courseId) {
}

package com.example.studentservice.dto;

public record EnrollResponse(Long studentId, Long courseId,
                             Long[] enrolledCourses,
                             Integer remainingCapacity,
                             String message) {
}

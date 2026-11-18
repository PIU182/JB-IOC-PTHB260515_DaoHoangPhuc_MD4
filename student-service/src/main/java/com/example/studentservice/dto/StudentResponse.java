package com.example.studentservice.dto;

import com.example.studentservice.entity.Student;

import java.util.Arrays;

public record StudentResponse(Long id, String name, String email, Long[] enrolledCourses) {
    public static StudentResponse from(Student s) {
        Long[] copy = Arrays.copyOf(s.getEnrolledCourses(), s.getEnrolledCourses().length);
        return new StudentResponse(s.getId(), s.getName(), s.getEmail(), copy);
    }
}

package com.example.courseservice.dto;

import com.example.courseservice.entity.Course;

public record CourseResponse(Long id, String courseName, Integer capacity, Integer credits) {
    public static CourseResponse from(Course c) {
        return new CourseResponse(c.getId(), c.getCourseName(), c.getCapacity(), c.getCredits());
    }
}

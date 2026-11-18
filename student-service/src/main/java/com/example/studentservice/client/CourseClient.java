package com.example.studentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "course-service", path = "/api/courses")
public interface CourseClient {
    record CourseInfo(Long id, String courseName, Integer capacity, Integer credits){}

    @GetMapping("/{courseId}")
    CourseInfo getCourse(@PathVariable("courseId") Long courseId);

    @PostMapping("/{courseId}/reserve")
    CourseInfo reserve(@PathVariable("courseId") Long courseId);
}

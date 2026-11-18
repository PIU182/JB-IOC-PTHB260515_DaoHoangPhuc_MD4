package com.example.courseservice.controller;

import com.example.courseservice.dto.CourseResponse;
import com.example.courseservice.dto.CreateCourseRequest;
import com.example.courseservice.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService service;

    @GetMapping("/{courseId}")
    public CourseResponse get(@PathVariable Long courseId) {
        return service.findOne(courseId);
    }

    @GetMapping
    public List<CourseResponse> list() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<CourseResponse> create(@Valid @RequestBody CreateCourseRequest request) {
        CourseResponse result = service.create(request);
        return ResponseEntity.created(URI.create("/api/courses" + result.id())).body(result);
    }

    @PostMapping("/{courseId}/reserve")
    public CourseResponse reserve(@PathVariable Long courseId) {
        return service.reserve(courseId);
    }
}

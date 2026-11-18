package com.example.studentservice.controller;

import com.example.studentservice.dto.CreateStudentRequest;
import com.example.studentservice.dto.EnrollRequest;
import com.example.studentservice.dto.EnrollResponse;
import com.example.studentservice.dto.StudentResponse;
import com.example.studentservice.entity.Student;
import com.example.studentservice.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService service;

    @GetMapping
    public List<StudentResponse> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public StudentResponse get(@PathVariable Long id) {
        return service.findOne(id);
    }

    @PostMapping
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody CreateStudentRequest request) {
        StudentResponse result = service.create(request);
        return ResponseEntity.created(URI.create("/api/students/" + result.id()))
                .body(result);
    }

    @PostMapping("/enroll")
    public ResponseEntity<EnrollResponse> enroll(@Valid @RequestBody EnrollRequest request) {
        return ResponseEntity.ok(service.enroll(request));
    }
}

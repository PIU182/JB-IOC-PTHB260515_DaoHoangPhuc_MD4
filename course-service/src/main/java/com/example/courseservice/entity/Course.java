package com.example.courseservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private Integer credits;

    public Course(String courseName, Integer capacity, Integer credits) {
        this.courseName = courseName;
        this.capacity = capacity;
        this.credits = credits;
    }
}

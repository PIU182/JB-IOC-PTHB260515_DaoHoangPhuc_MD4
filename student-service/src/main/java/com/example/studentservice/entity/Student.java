package com.example.studentservice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Arrays;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "enrolled_courses", columnDefinition = "bigint[]", nullable = false)
    private Long[] enrolledCourses = new Long[0];

    public Student(String name, String email)
    {
    this.name = name;
    this.email = email;
    }

    public boolean hasCourse(Long courseId) {
        return Arrays.asList(enrolledCourses).contains(courseId);
    }

    public void addCourse(Long courseId)
    {
        Long[] next = Arrays.copyOf(enrolledCourses, enrolledCourses.length + 1);
        next[next.length - 1] = courseId;
        this.enrolledCourses = next;
    }
}

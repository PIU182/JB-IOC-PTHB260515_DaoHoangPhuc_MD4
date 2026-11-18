package com.example.courseservice.config;

import com.example.courseservice.entity.Course;
import com.example.courseservice.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CourseSeedConfig {
    @Bean
    CommandLineRunner seedCourse(CourseRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
                        new Course("Java spring boot", 2, 3),
                        new Course("Microseve", 1, 4),
                        new Course("Postgres", 0, 3)
                ));
            }
        };
    }
}

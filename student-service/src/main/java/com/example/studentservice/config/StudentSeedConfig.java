package com.example.studentservice.config;

import com.example.studentservice.entity.Student;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class StudentSeedConfig {
    @Bean
    CommandLineRunner seedStudent(StudentRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
                        new Student("Nguyen An", "an@example.com"),
                        new Student("tran binh", "binh@example.com")));
            }
        };
    }
}

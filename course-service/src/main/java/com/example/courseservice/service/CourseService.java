package com.example.courseservice.service;

import com.example.courseservice.dto.CourseResponse;
import com.example.courseservice.dto.CreateCourseRequest;
import com.example.courseservice.entity.Course;
import com.example.courseservice.repository.CourseRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository repository;

    @Transactional(readOnly = true)
    public CourseResponse findOne(Long id) {
        return CourseResponse.from(repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Khoa hoc khong ton tai")));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> findAll() {
        return repository.findAll().stream().map(CourseResponse::from).toList();
    }

    @Transactional
    public CourseResponse create(CreateCourseRequest request) {
        Course course = new Course(request.courseName().trim(),
                request.capacity(),request.credits());
        return CourseResponse.from(repository.save(course));
    }

    @Transactional
    public CourseResponse reserve(Long courseId) {
        int changed = repository.reserveOneSeat((courseId));
        if (changed == 0) {
            if (!repository.existsById(courseId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Khoa hoc khong ton tai");
            }
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Khoa hoc da het cho");
        }
        return findOne(courseId);
    }
}

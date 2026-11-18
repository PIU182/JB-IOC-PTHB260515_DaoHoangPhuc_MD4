package com.example.studentservice.service;

import com.example.studentservice.client.CourseClient;
import com.example.studentservice.dto.CreateStudentRequest;
import com.example.studentservice.dto.EnrollRequest;
import com.example.studentservice.dto.EnrollResponse;
import com.example.studentservice.dto.StudentResponse;
import com.example.studentservice.entity.Student;
import com.example.studentservice.repository.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository students;
    private final CourseClient courses;

    @Transactional(readOnly = true)
    public StudentResponse findOne(Long id) {
        return StudentResponse.from(students.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sinh vien khong ton tai")));
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> findAll() {
        return students.findAll().stream().map(StudentResponse::from).toList();
    }

    @Transactional
    public StudentResponse create(CreateStudentRequest input) {
        String email = input.email().trim().toLowerCase(Locale.ROOT);
        if (students.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email da ton tai");
        }
        return StudentResponse.from(students.save(new Student(input.name().trim(), email)));
    }

    @Transactional
    public EnrollResponse enroll(EnrollRequest input) {
        Student student = students.findForEnrollment(input.studentId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Sinh vien da dang ky khoa hoc nay"
                ));

        CourseClient.CourseInfo course = courses.getCourse(input.courseId());
        if (course.capacity() <= 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Khoa hoc da het cho");
        }

        CourseClient.CourseInfo reserved = courses.reserve(input.courseId());

        student.addCourse(input.courseId());
        students.saveAndFlush(student);
        return new EnrollResponse(student.getId(), input.courseId(),
                student.getEnrolledCourses(), reserved.capacity(), "Dang ky khoa hoc thanh cong");
    }

}

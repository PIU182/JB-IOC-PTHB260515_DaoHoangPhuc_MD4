package com.example.courseservice.repository;

import com.example.courseservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update Course c set c .capacity = c.capacity - 1" + " where c.id = :id and c.capacity > 0")
    int reserveOneSeat(@Param("id") Long id);
}

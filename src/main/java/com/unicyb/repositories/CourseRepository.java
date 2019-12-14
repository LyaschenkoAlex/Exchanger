package com.unicyb.repositories;

import com.unicyb.data.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query(value = "SELECT max(id) FROM exchanger.course", nativeQuery = true)
    int maxId();
}

package com.example.exammanagementsystem.repository;

import com.example.exammanagementsystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}

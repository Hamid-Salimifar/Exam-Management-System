package com.example.exammanagementsystem.repository;

import com.example.exammanagementsystem.model.Course;
import com.example.exammanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findByTeacher(User teacher);
}

package com.example.exammanagementsystem.service;

import com.example.exammanagementsystem.model.Course;
import com.example.exammanagementsystem.model.User;

import java.time.LocalDate;
import java.util.List;

public interface CourseService extends BaseService<Course> {

    Course createCourse(String uniqueCode, LocalDate startDate, LocalDate endDate, Long teacherId, List<Long> studentIds);

    Course updateCourse(Long courseId, String uniqueCode, LocalDate startDate, LocalDate endDate, Long teacherId, List<Long> studentIds);

    List<Course> findByTeacher(User teacher);
}



package com.example.exammanagementsystem.repository;

import com.example.exammanagementsystem.model.Course;
import com.example.exammanagementsystem.model.Question;
import com.example.exammanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByTeacherAndCourse(User teacher, Course course);
}

package com.example.exammanagementsystem.service;

import com.example.exammanagementsystem.model.Course;
import com.example.exammanagementsystem.model.Question;
import com.example.exammanagementsystem.model.User;

import java.util.List;

public interface QuestionService extends BaseService<Question>{
    List<Question> findByTeacherAndCourse(User teacher, Course course);
}

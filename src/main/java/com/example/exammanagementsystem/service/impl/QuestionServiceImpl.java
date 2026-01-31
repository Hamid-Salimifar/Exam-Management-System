package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.Course;
import com.example.exammanagementsystem.model.Question;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.repository.QuestionRepository;
import com.example.exammanagementsystem.service.QuestionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {
    private final QuestionRepository questionRepository;
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        super(questionRepository);
        this.questionRepository=questionRepository;
    }

    @Override
    public List<Question> findByTeacherAndCourse(User teacher, Course course) {
       return questionRepository.findByTeacherAndCourse(teacher,course);
    }
}

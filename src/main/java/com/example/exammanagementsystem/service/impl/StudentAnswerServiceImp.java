package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.*;
import com.example.exammanagementsystem.repository.StudentAnswerRepository;
import com.example.exammanagementsystem.service.ExamAttemptService;
import com.example.exammanagementsystem.service.StudentAnswerService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentAnswerServiceImp extends BaseServiceImpl<StudentAnswer> implements StudentAnswerService {
    private final StudentAnswerRepository studentAnswerRepository;
    private final ExamAttemptService examAttemptService;
    public StudentAnswerServiceImp(StudentAnswerRepository studentAnswerRepository,ExamAttemptService examAttemptService) {
        super(studentAnswerRepository);
        this.studentAnswerRepository=studentAnswerRepository;
        this.examAttemptService=examAttemptService;
    }

    @Override
    public List<StudentAnswer> findByExamAttempt(ExamAttempt examAttempt) {
        return studentAnswerRepository.findByExamAttempt(examAttempt);
    }

    @Override
    public Optional<StudentAnswer> findByExamAttemptAndExamQuestion(ExamAttempt examAttempt, ExamQuestion examQuestion) {
        return studentAnswerRepository.findByExamAttemptAndExamQuestion(examAttempt,examQuestion);
    }




}

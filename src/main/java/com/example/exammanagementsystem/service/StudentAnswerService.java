package com.example.exammanagementsystem.service;

import com.example.exammanagementsystem.model.*;

import java.util.List;
import java.util.Optional;

public interface StudentAnswerService extends BaseService<StudentAnswer> {
    Optional<StudentAnswer> findByExamAttemptAndExamQuestion(ExamAttempt examAttempt, ExamQuestion examQuestion);

    List<StudentAnswer> findByExamAttempt(ExamAttempt examAttempt);

//    DescriptiveAnswer createStudentDescriptiveAnswer(ExamQuestion examQuestion, Long examAttemptId, String descriptiveAnswerText);
//
//    McqAnswer creatStudentMcqAnswer(ExamQuestion examQuestion, Long examAttemptId, Long mcqAnswer);
}

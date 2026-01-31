package com.example.exammanagementsystem.service;

import com.example.exammanagementsystem.model.ExamAttempt;
import com.example.exammanagementsystem.model.ExamQuestion;
import com.example.exammanagementsystem.model.McqAnswer;

import java.util.List;
import java.util.Optional;

public interface McqAnswerService extends BaseService<McqAnswer> {
    McqAnswer creatOrUpdateStudentMcqAnswer(ExamQuestion examQuestion, Long examAttemptId, Long mcqAnswer);
    Optional<McqAnswer> findByExamAttemptIdAndExamQuestionId(
            Long examAttemptId,
            Long examQuestionId
    );

    List<McqAnswer> findByExamAttempt(ExamAttempt examAttempt);
}

package com.example.exammanagementsystem.service;

import com.example.exammanagementsystem.model.DescriptiveAnswer;
import com.example.exammanagementsystem.model.ExamAttempt;
import com.example.exammanagementsystem.model.ExamQuestion;
import tools.jackson.core.ObjectReadContext;

import java.util.List;
import java.util.Optional;

public interface DescriptiveAnswerService extends BaseService<DescriptiveAnswer> {
    DescriptiveAnswer createOrUpdateStudentDescriptiveAnswer(ExamQuestion examQuestion, Long examAttemptId, String descriptiveAnswerText);
    Optional<DescriptiveAnswer> findByExamAttemptIdAndExamQuestionId(
            Long examAttemptId,
            Long examQuestionId
    );
    List<DescriptiveAnswer> findByExamAttempt(ExamAttempt examAttempt);
}

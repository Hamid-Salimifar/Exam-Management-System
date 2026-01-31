package com.example.exammanagementsystem.service;

import com.example.exammanagementsystem.model.Exam;
import com.example.exammanagementsystem.model.ExamQuestion;

import java.util.List;

public interface ExamQuestionService extends BaseService<ExamQuestion> {
    List<ExamQuestion> findByExam(Exam exam);

    void attachQuestions(Long examId, List<Long> questionIds);
    List<ExamQuestion> findByExamOrderByIdAsc(Exam exam);
}

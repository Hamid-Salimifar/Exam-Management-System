package com.example.exammanagementsystem.service;

import com.example.exammanagementsystem.model.Exam;
import com.example.exammanagementsystem.model.ExamAttempt;
import com.example.exammanagementsystem.model.ExamAttemptStatus;
import com.example.exammanagementsystem.model.User;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptService extends BaseService<ExamAttempt>{

    boolean existsByExamAndStudentAndStatus(Exam exam, User student, ExamAttemptStatus status);

    List<Exam> validExamForAStudent(List<Exam> exams, User student);

    ExamAttempt startExam(Exam exam, User student);
    ExamAttempt findByExamAndStudent(Exam exam, User student);

    List<ExamAttempt> findByExam(Exam exam);
}

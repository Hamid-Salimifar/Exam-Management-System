package com.example.exammanagementsystem.repository;

import com.example.exammanagementsystem.model.ExamAttempt;
import com.example.exammanagementsystem.model.ExamQuestion;
import com.example.exammanagementsystem.model.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer,Long> {
    
    Optional<StudentAnswer> findByExamAttemptAndExamQuestion(ExamAttempt examAttempt, ExamQuestion examQuestion);
    List<StudentAnswer> findByExamAttempt(ExamAttempt examAttempt);
}

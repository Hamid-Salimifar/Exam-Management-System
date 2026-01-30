package com.example.exammanagementsystem.repository;

import com.example.exammanagementsystem.model.ExamAttempt;
import com.example.exammanagementsystem.model.McqAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface McqAnswerRepository extends JpaRepository<McqAnswer,Long> {
    Optional<McqAnswer> findByExamAttemptIdAndExamQuestionId(
            Long examAttemptId,
            Long examQuestionId
    );

    List<McqAnswer> findByExamAttempt(ExamAttempt examAttempt);

}

package com.example.exammanagementsystem.repository;

import com.example.exammanagementsystem.model.DescriptiveAnswer;
import com.example.exammanagementsystem.model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import tools.jackson.databind.node.LongNode;

import java.util.List;
import java.util.Optional;

public interface DescriptiveAnswerRepository extends JpaRepository<DescriptiveAnswer, Long> {
    Optional<DescriptiveAnswer> findByExamAttemptIdAndExamQuestionId(
            Long examAttemptId,
            Long examQuestionId
    );

    List<DescriptiveAnswer> findByExamAttempt(ExamAttempt examAttempt);
}

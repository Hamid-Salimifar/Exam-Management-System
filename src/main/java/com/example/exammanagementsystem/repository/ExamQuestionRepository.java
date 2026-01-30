package com.example.exammanagementsystem.repository;

import com.example.exammanagementsystem.model.Exam;
import com.example.exammanagementsystem.model.ExamQuestion;
import com.example.exammanagementsystem.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamQuestionRepository extends JpaRepository<ExamQuestion,Long> {

    List<ExamQuestion> findByExam(Exam exam);
    boolean existsByExamAndQuestion(Exam exam, Question question);
    List<ExamQuestion> findByExamOrderByIdAsc(Exam exam);
}

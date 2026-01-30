package com.example.exammanagementsystem.repository;

import com.example.exammanagementsystem.model.Exam;
import com.example.exammanagementsystem.model.ExamAttempt;
import com.example.exammanagementsystem.model.ExamAttemptStatus;
import com.example.exammanagementsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt,Long> {
    boolean existsByExamAndStudentAndStatus(Exam exam, User student, ExamAttemptStatus status);
    Optional<ExamAttempt> findByExamAndStudent(Exam exam, User student);
    List<ExamAttempt> findByExam(Exam exam);
}

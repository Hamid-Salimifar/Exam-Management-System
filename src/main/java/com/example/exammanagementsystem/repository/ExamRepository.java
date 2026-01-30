package com.example.exammanagementsystem.repository;

import com.example.exammanagementsystem.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam,Long> {
    List<Exam> findByTeacher_Id(Long teacherId);

    List<Exam> findByCourse_IdAndTeacher_Id(Long courseId, Long teacherId);

    List<Exam> findByCourse_Id(Long courseId);
}

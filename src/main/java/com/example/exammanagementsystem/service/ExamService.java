package com.example.exammanagementsystem.service;

import com.example.exammanagementsystem.model.Exam;

import java.util.List;

public interface ExamService extends BaseService<Exam>{

    List<Exam> findByTeacher_Id(Long teacherId);

    List<Exam> findByCourse_IdAndTeacher_Id(Long courseId, Long teacherId);

    Exam updateExam(Long id, Exam updatedExam);

    List<Exam> findByCourse_Id(Long courseId);
}

package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.Exam;
import com.example.exammanagementsystem.repository.ExamRepository;
import com.example.exammanagementsystem.service.ExamService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExamServiceImpl extends BaseServiceImpl<Exam> implements ExamService {

    private  final ExamRepository  examRepository;
    public ExamServiceImpl(ExamRepository examRepository) {
        super(examRepository);
        this.examRepository=examRepository;
    }


    @Override
    public List<Exam> findByCourse_Id(Long courseId) {
        return examRepository.findByCourse_Id(courseId);
    }

    @Override
    public List<Exam> findByTeacher_Id(Long teacherId) {
        return examRepository.findByTeacher_Id(teacherId);
    }

    @Override
    public List<Exam> findByCourse_IdAndTeacher_Id(Long courseId, Long teacherId) {
        //todo:add...
       return examRepository.findByCourse_IdAndTeacher_Id(courseId,teacherId);
    }

    @Override
    public Exam updateExam(Long id, Exam updatedExam) {
        Exam exam = examRepository.findById(updatedExam.getId()).orElseThrow();
        exam.setTitle(updatedExam.getTitle());
        exam.setDescription(updatedExam.getDescription());
        exam.setStartTime(updatedExam.getStartTime());
        exam.setEndTime(updatedExam.getEndTime());
        exam.setDurationMinutes(updatedExam.getDurationMinutes());

        return examRepository.save(exam);


    }
}

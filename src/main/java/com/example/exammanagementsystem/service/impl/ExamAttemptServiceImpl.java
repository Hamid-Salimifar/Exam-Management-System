package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.Exam;
import com.example.exammanagementsystem.model.ExamAttempt;
import com.example.exammanagementsystem.model.ExamAttemptStatus;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.repository.ExamAttemptRepository;
import com.example.exammanagementsystem.repository.ExamRepository;
import com.example.exammanagementsystem.service.ExamAttemptService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExamAttemptServiceImpl extends BaseServiceImpl<ExamAttempt> implements ExamAttemptService {
    private final ExamAttemptRepository examAttemptRepository;
    public ExamAttemptServiceImpl(ExamAttemptRepository examAttemptRepository) {
        super(examAttemptRepository);
        this.examAttemptRepository=examAttemptRepository;
    }


    @Override
    public boolean existsByExamAndStudentAndStatus(Exam exam, User student, ExamAttemptStatus status) {
        return examAttemptRepository.existsByExamAndStudentAndStatus(exam,student,status);
    }

    @Override
    public List<Exam> validExamForAStudent(List<Exam> exams, User student) {
        LocalDateTime now = LocalDateTime.now();

        return exams.stream()
                .filter(exam -> !existsByExamAndStudentAndStatus(exam, student,ExamAttemptStatus.SUBMITTED))
                .filter(exam -> !exam.getStartTime().isAfter(now))
                .filter(exam -> !exam.getEndTime().isBefore(now))
                .toList();
    }

    @Override
    public ExamAttempt startExam(Exam exam, User student) {
        Optional<ExamAttempt> existing =
                examAttemptRepository.findByExamAndStudent(exam, student);

        if (existing.isPresent()) {
            return existing.get();
        }
        LocalDateTime start = LocalDateTime.now();
        ExamAttempt examAttempt = ExamAttempt.builder()
                .exam(exam)
                .student(student)
                .startTime(start)
                .endTime(start.plusMinutes(exam.getDurationMinutes()))

                .status(ExamAttemptStatus.IN_PROGRESS)
                .build();
        return saveOrUpdate(examAttempt);
    }

    @Override
    public ExamAttempt findByExamAndStudent(Exam exam, User student) {
        return examAttemptRepository.findByExamAndStudent(exam,student).orElseThrow();
    }

    @Override
    public List<ExamAttempt> findByExam(Exam exam) {
        return examAttemptRepository.findByExam(exam);
    }
}

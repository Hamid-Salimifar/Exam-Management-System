package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.DescriptiveAnswer;
import com.example.exammanagementsystem.model.ExamAttempt;
import com.example.exammanagementsystem.model.ExamQuestion;
import com.example.exammanagementsystem.repository.DescriptiveAnswerRepository;
import com.example.exammanagementsystem.service.DescriptiveAnswerService;
import com.example.exammanagementsystem.service.ExamAttemptService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DescriptiveAnswerServiceImpl extends BaseServiceImpl<DescriptiveAnswer> implements DescriptiveAnswerService {
    private final ExamAttemptService examAttemptService;
    private final DescriptiveAnswerRepository descriptiveAnswerRepository;

    public DescriptiveAnswerServiceImpl(DescriptiveAnswerRepository descriptiveAnswerRepository, ExamAttemptService examAttemptService) {
        super(descriptiveAnswerRepository);
        this.descriptiveAnswerRepository = descriptiveAnswerRepository;
        this.examAttemptService = examAttemptService;
    }

    @Override
    public List<DescriptiveAnswer> findByExamAttempt(ExamAttempt examAttempt) {
        return descriptiveAnswerRepository.findByExamAttempt(examAttempt);
    }

    @Override
    public DescriptiveAnswer createOrUpdateStudentDescriptiveAnswer(ExamQuestion examQuestion, Long examAttemptId, String descriptiveAnswerText) {
        ExamAttempt examAttempt = examAttemptService.findById(examAttemptId);
        DescriptiveAnswer answer = descriptiveAnswerRepository.findByExamAttemptIdAndExamQuestionId(examAttemptId, examQuestion.getId()).orElse(null);
        if (answer == null) {
            answer = new DescriptiveAnswer();
            answer.setExamQuestion(examQuestion);
            answer.setExamAttempt(examAttempt);
        }

        answer.setAnswerText(descriptiveAnswerText);

        return saveOrUpdate(answer);
    }

    @Override
    public Optional<DescriptiveAnswer> findByExamAttemptIdAndExamQuestionId(Long examAttemptId, Long examQuestionId) {
        return descriptiveAnswerRepository.findByExamAttemptIdAndExamQuestionId(examAttemptId,examQuestionId);
    }
}

package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.*;
import com.example.exammanagementsystem.repository.McqAnswerRepository;
import com.example.exammanagementsystem.service.ExamAttemptService;
import com.example.exammanagementsystem.service.McqAnswerService;
import com.example.exammanagementsystem.service.OptionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class McqAnswerServiceImpl extends BaseServiceImpl<McqAnswer> implements McqAnswerService {
    private final McqAnswerRepository mcqAnswerRepository;
    private final ExamAttemptService examAttemptService;
    private final OptionService optionService;

    public McqAnswerServiceImpl(McqAnswerRepository mcqAnswerRepository,ExamAttemptService examAttemptService,OptionService optionService) {
        super(mcqAnswerRepository);
        this.mcqAnswerRepository=mcqAnswerRepository;
        this.examAttemptService=examAttemptService;
        this.optionService=optionService;
    }

    @Override
    public List<McqAnswer> findByExamAttempt(ExamAttempt examAttempt) {
        return mcqAnswerRepository.findByExamAttempt(examAttempt);
    }

    @Override
    public McqAnswer creatOrUpdateStudentMcqAnswer(ExamQuestion examQuestion, Long examAttemptId, Long mcqAnswer) {
        ExamAttempt examAttempt = examAttemptService.findById(examAttemptId);
        Option option = optionService.findById(mcqAnswer);
        McqAnswer answer = mcqAnswerRepository.findByExamAttemptIdAndExamQuestionId(examAttemptId, examQuestion.getId()).orElse(null);
        if (!option.getQuestion().getId().equals(examQuestion.getQuestion().getId())) {
            throw new IllegalArgumentException("Option does not belong to this question");
        }
        if (answer == null) {
            answer = new McqAnswer();
            answer.setExamQuestion(examQuestion);
            answer.setExamAttempt(examAttempt);
        }

        answer.setSelectedOption(option);

        if (option.isCorrect()) {
            answer.setScore(examQuestion.getScore()); // dynamic score
        } else {
            answer.setScore(0);
        }

        return saveOrUpdate(answer);
    }

    @Override
    public Optional<McqAnswer> findByExamAttemptIdAndExamQuestionId(Long examAttemptId, Long examQuestionId) {
        return mcqAnswerRepository.findByExamAttemptIdAndExamQuestionId(examAttemptId,examQuestionId);
    }
}

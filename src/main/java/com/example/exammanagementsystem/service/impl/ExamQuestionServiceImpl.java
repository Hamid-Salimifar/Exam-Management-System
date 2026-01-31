package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.Exam;
import com.example.exammanagementsystem.model.ExamQuestion;
import com.example.exammanagementsystem.model.Question;
import com.example.exammanagementsystem.repository.ExamQuestionRepository;
import com.example.exammanagementsystem.repository.QuestionRepository;
import com.example.exammanagementsystem.service.ExamQuestionService;
import com.example.exammanagementsystem.service.ExamService;
import com.example.exammanagementsystem.service.QuestionService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;



@Service
public class ExamQuestionServiceImpl extends BaseServiceImpl<ExamQuestion> implements ExamQuestionService {

    private final ExamQuestionRepository examQuestionRepository;
    private final QuestionService questionService;
    private final ExamService examService;
    public ExamQuestionServiceImpl(ExamQuestionRepository examQuestionRepository,QuestionService questionService,ExamService examService) {
        super(examQuestionRepository);
        this.examQuestionRepository=examQuestionRepository;
        this.questionService=questionService;
        this.examService=examService;
    }


    @Override
    public List<ExamQuestion> findByExam(Exam exam) {
        List<ExamQuestion> questions = examQuestionRepository.findByExam(exam);
        return questions != null ? questions : Collections.emptyList();

    }

    @Override
    public List<ExamQuestion> findByExamOrderByIdAsc(Exam exam) {
        return examQuestionRepository.findByExamOrderByIdAsc(exam);
    }

    @Override
    public void attachQuestions(Long examId, List<Long> questionIds) {
        Exam exam = examService.findById(examId);

        for (Long id:questionIds) {
            Question q = questionService.findById(id);



            if (examQuestionRepository.existsByExamAndQuestion(exam, q)) {
                continue;
            }


            ExamQuestion eq = new ExamQuestion();
            eq.setExam(exam);
            eq.setQuestion(q);
            eq.setScore(1);
            examQuestionRepository.save(eq);
        }

    }
}

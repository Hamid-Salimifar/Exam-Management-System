package com.example.exammanagementsystem.controller;

import com.example.exammanagementsystem.model.*;
import com.example.exammanagementsystem.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final UserService userService;
    private final CourseService courseService;
    private final ExamService examService;
    private final ExamAttemptService examAttemptService;
    private final ExamQuestionService examQuestionService;
    private final StudentAnswerService studentAnswerService;
    private final DescriptiveAnswerService descriptiveAnswerService;
    private final McqAnswerService mcqAnswerService;

    @GetMapping("/courses")
    public String pageToSeeCourses(Model model, Principal principal) {
        User student = userService.findByUsername(principal.getName()).orElseThrow();
        List<Course> courses = courseService.findByStudent(student);
        model.addAttribute("courses", courses);
        return "student-courses";
    }

    @GetMapping("/course/exams/{courseId}")
    public String pageToShowAllTheExamsForCourseToStudent(@PathVariable Long courseId, Model model, Principal principal) {
        List<Exam> exams = examService.findByCourse_Id(courseId);
        User student = userService.findByUsername(principal.getName()).orElseThrow();
        List<Exam> validExams = examAttemptService.validExamForAStudent(exams, student);
        model.addAttribute("exams", validExams);
        return "student-exams";
    }

    @GetMapping("/exam/{examId}")
    public String takingExam(@PathVariable Long examId, Model model, Principal principal) {
        User student = userService.findByUsername(principal.getName()).orElseThrow();
        Exam exam = examService.findById(examId);
        ExamAttempt attempt = examAttemptService.startExam(exam, student);
        if (attempt.getStatus() == ExamAttemptStatus.SUBMITTED) {
            return "redirect:/student/courses"; // or show message
        }
//        List<ExamQuestion> examQuestions = examQuestionService.findByExam(exam);
//        ExamQuestion currentQuestion= examQuestions.get(examAttempt.getCurrentQuestionIndex());
//        model.addAttribute("duration",exam.getDurationMinutes());
//        model.addAttribute("startTime",examAttempt.getStartTime());
//        model.addAttribute("question",currentQuestion);
//        model.addAttribute("examAttempt",examAttempt);
        return "redirect:/student/exam/" + examId + "/question/0";
    }

    @GetMapping("/exam/{examId}/question/{index}")
    public String showExamQuestion(@PathVariable Long examId,
                                   @PathVariable int index,
                                   Model model,
                                   Principal principal){

        User student = userService.findByUsername(principal.getName()).orElseThrow();
        Exam exam = examService.findById(examId);
        ExamAttempt examAttempt = examAttemptService.findByExamAndStudent(exam, student);
        List<ExamQuestion> examQuestions = examQuestionService.findByExamOrderByIdAsc(exam);
        //############################################
        // TIME CHECK
        LocalDateTime now = LocalDateTime.now();

        if (examAttempt.getEndTime().isBefore(now)) {
            examAttempt.setStatus(ExamAttemptStatus.SUBMITTED);
            examAttemptService.saveOrUpdate(examAttempt);
            return "redirect:/student/courses"; // or finished page
        }

        // Remaining time (seconds)
        long remainingSeconds =
                Duration.between(now, examAttempt.getEndTime()).getSeconds();

        model.addAttribute("remainingSeconds", remainingSeconds);
        //#############################################################################
//        //ToDo add a proper error
//        // safety check
//        if (index < 0 || index >= examQuestions.size()) {
//            return "redirect:/courses"; // or show error page
//        }

        ExamQuestion currentQuestion = examQuestions.get(index);

        StudentAnswer studentAnswer = null;
        if(currentQuestion.getQuestion().getQuestionType() == QuestionType.DESCRIPTIVE) {
            studentAnswer = descriptiveAnswerService
                    .findByExamAttemptIdAndExamQuestionId(examAttempt.getId(), currentQuestion.getId())
                    .orElse(null);
        } else if(currentQuestion.getQuestion().getQuestionType() == QuestionType.MCQ) {
            studentAnswer = mcqAnswerService
                    .findByExamAttemptIdAndExamQuestionId(examAttempt.getId(), currentQuestion.getId())
                    .orElse(null);
        }

        model.addAttribute("examQuestion",currentQuestion);
        model.addAttribute("examAttempt",examAttempt);
        model.addAttribute("exam",exam);
        model.addAttribute("allExamQuestions",examQuestions);
        model.addAttribute("index",index);
        model.addAttribute("studentAnswer", studentAnswer);
        return "student-show-each-question";
    }

    @PostMapping("/exam/{examId}/question/{index}")
    public String answerQuestion(@PathVariable Long examId,
                                 @PathVariable int index,
                                 @RequestParam Long examQuestionId,
                                 @RequestParam Long examAttemptId,
                                 @RequestParam(required = false) String descriptiveAnswerText,
                                 @RequestParam(required = false) Long mcqAnswer){
        ExamAttempt examAttempt1 = examAttemptService.findById(examAttemptId);

        if (examAttempt1.getEndTime().isBefore(LocalDateTime.now())) {
            examAttempt1.setStatus(ExamAttemptStatus.SUBMITTED);
            examAttemptService.saveOrUpdate(examAttempt1);
            return "redirect:/student/courses";
        }
        ExamQuestion examQuestion = examQuestionService.findById(examQuestionId);
//        if(examQuestion.getQuestion().getQuestionType() == QuestionType.DESCRIPTIVE){
//            descriptiveAnswerService.createOrUpdateStudentDescriptiveAnswer(examQuestion,examAttemptId,descriptiveAnswerText);
//        } else if (examQuestion.getQuestion().getQuestionType() == QuestionType.MCQ) {
//            mcqAnswerService.creatOrUpdateStudentMcqAnswer(examQuestion,examAttemptId,mcqAnswer);
//        }
        if(examQuestion.getQuestion().getQuestionType() == QuestionType.DESCRIPTIVE){
            if(descriptiveAnswerText != null && !descriptiveAnswerText.isBlank()) {
                descriptiveAnswerService.createOrUpdateStudentDescriptiveAnswer(examQuestion, examAttemptId, descriptiveAnswerText);
            }
        } else if(examQuestion.getQuestion().getQuestionType() == QuestionType.MCQ){
            if(mcqAnswer != null) {
                mcqAnswerService.creatOrUpdateStudentMcqAnswer(examQuestion, examAttemptId, mcqAnswer);
            }
        }

        List<ExamQuestion> examQuestions = examQuestionService.findByExamOrderByIdAsc(examQuestion.getExam());
        boolean isLastQuestion = index + 1 == examQuestions.size();
        if(isLastQuestion) {
            // mark exam finished
            ExamAttempt examAttempt = examAttemptService.findById(examAttemptId);
            examAttempt.setStatus(ExamAttemptStatus.SUBMITTED);
            examAttempt.setEndTime(LocalDateTime.now());
            // optionally calculate total score here
            examAttemptService.saveOrUpdate(examAttempt);
            return "redirect:/student/courses"; // or wherever you want
        }else {

        }
        int newIndex =index+1;
        return "redirect:/student/exam/"+examId+"/question/"+newIndex;
    }

}

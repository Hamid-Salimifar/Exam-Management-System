package com.example.exammanagementsystem.controller;

import com.example.exammanagementsystem.model.*;
import com.example.exammanagementsystem.repository.QuestionRepository;
import com.example.exammanagementsystem.service.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final CourseService courseService;
    private final UserService userService;
    private final ExamService examService;
    private final QuestionService questionService;
    private final ExamQuestionService examQuestionService;
    private final ExamAttemptService examAttemptService;
    private final StudentAnswerService studentAnswerService;
    private final DescriptiveAnswerService descriptiveAnswerService;
    private final McqAnswerService mcqAnswerService;

    @GetMapping("/courses")
    public String seeAllCoursesOfTeacher(Model model, Principal principal) {
        String teacherName = principal.getName();
        User teacher = userService.findByUsername(teacherName).orElseThrow();
        List<Course> coursesOfTeacher = courseService.findByTeacher(teacher);
        model.addAttribute("coursesOfTeacher", coursesOfTeacher);
        return "courses-of-teacher";

    }

    @GetMapping("/course/exams/{id}")
    public String showAllExamOfACourseOfATeacher(@PathVariable Long id, Principal principal, Model model) {
        User teacher = userService.findByUsername(principal.getName()).orElseThrow();
        List<Exam> examsByCourseIdAndTeacherId = examService.findByCourse_IdAndTeacher_Id(id, teacher.getId());
        model.addAttribute("courseId", id);
        model.addAttribute("allExam", examsByCourseIdAndTeacherId);
        return "exam-course-teacher";
    }

    @GetMapping("/course/exam/edit/{id}")
    public String showEditPageForExam(@PathVariable Long id, Model model) {
        Exam exam = examService.findById(id);
        model.addAttribute("exam", exam);
        return "edit-exam";
    }

    @PostMapping("/course/exam/edit/{id}")
    public String editExam(@PathVariable Long id, @ModelAttribute Exam exam) {
        Exam exam1 = examService.updateExam(id, exam);
        return "redirect:/teacher/course/exams/" + exam.getCourse().getId();
    }

    @PostMapping("/course/exam/delete/{id}")
    public String deleteExam(@PathVariable Long id) {
        Exam exam = examService.findById(id);
        examService.deleteById(id);
        return "redirect:/teacher/course/exams/" + exam.getCourse().getId();
    }


    @GetMapping("/course/add/exam/{id}")
    public String goToAddExamPage(@PathVariable Long id, Model model) {
        model.addAttribute("exam", new Exam());
        model.addAttribute("courseId",id);
        return "add-exam";

    }

    @PostMapping("/course/add/exam")
    public String addExam(@RequestParam Long courseId,@ModelAttribute Exam exam,Principal principal){
        User teacher = userService.findByUsername(principal.getName()).orElseThrow();
        Course course = courseService.findById(courseId);

        exam.setTeacher(teacher);
        exam.setCourse(course);
        examService.saveOrUpdate(exam);
        return "redirect:/teacher/course/exams/" +courseId;


    }

    //***************  phase three ***********************

    @GetMapping("/course/exams/question/{examId}")
    public String questionsOfCourse(@PathVariable Long examId, Model model,Principal principal){
        Exam exam = examService.findById(examId);
        User teacher = userService.findByUsername(principal.getName()).orElseThrow();
        Course course = courseService.findById(exam.getCourse().getId());
        //this is the questionBank
        List<Question> QuestionsByTeacherAndCourse = questionService.findByTeacherAndCourse(teacher, course);
        //this is the specific question for this exam
        List<ExamQuestion> questionByExam = examQuestionService.findByExam(exam);
        Set<Long> examQuestionIds = questionByExam.stream().map(eq->eq.getQuestion().getId()).collect(Collectors.toSet());

        model.addAttribute("questionsBank",QuestionsByTeacherAndCourse);
        model.addAttribute("examQuestionIds",examQuestionIds);
        model.addAttribute("examId",examId);
        return "show-questionBank";
    }

    @PostMapping("/course/exams/question/add")
    public String addQuestionToExam(@RequestParam Long examId,
                                    @RequestParam(required = false) List<Long> questionIds
                                    ){
        if (questionIds != null) {
            examQuestionService.attachQuestions(examId, questionIds);
        }
        return "redirect:/teacher/course/exams/question/" + examId;

    }

    @GetMapping("/course/questions/{courseId}")
    public String createNewQuestionPage(@PathVariable Long courseId,Principal principal,Model model){
        User teacher = userService.findByUsername(principal.getName()).orElseThrow();
        model.addAttribute("courseId",courseId);
        return "create-new-question";
    }

    @PostMapping("/course/question/create/descriptive")
    public String createDescriptiveQuestion(
            @RequestParam Long courseId,
            @RequestParam String title,
            @RequestParam String questionText,
            Principal principal) {
        User teacher = userService.findByUsername(principal.getName()).orElseThrow();
        Course course = courseService.findById(courseId);
        DescriptiveQuestion question = new DescriptiveQuestion();
        question.setTitle(title);
        question.setQuestionText(questionText);
        question.setTeacher(teacher);
        question.setCourse(course);
        question.setQuestionType(QuestionType.DESCRIPTIVE);

        questionService.saveOrUpdate(question);

        return "redirect:/teacher/course/questions/" + courseId;
    }

    @PostMapping("/course/question/create/mcq")
    public String createMcqQuestion(
            @RequestParam Long courseId,
            @RequestParam String title,
            @RequestParam String questionText,
            @RequestParam List<String> optionTexts,
            @RequestParam Long correctOptionIndex,
            Principal principal) {

        User teacher = userService.findByUsername(principal.getName()).orElseThrow();
        Course course = courseService.findById(courseId);

        McqQuestion mcq = new McqQuestion();
        mcq.setTitle(title);
        mcq.setQuestionText(questionText);
        mcq.setTeacher(teacher);
        mcq.setCourse(course);
        mcq.setQuestionType(QuestionType.MCQ);

        List<Option> options = new ArrayList<>();
        for (int i = 0; i < optionTexts.size(); i++) {
            Option option = new Option();
            option.setText(optionTexts.get(i));
            option.setCorrect(i == correctOptionIndex);
            option.setQuestion(mcq);
            options.add(option);
        }
        mcq.setOptions(options);

        questionService.saveOrUpdate(mcq);

        return "redirect:/teacher/course/questions/" + courseId;
    }


    @GetMapping("/course/exam/{examId}/set-scores")
    public String showExamQuestionsForScoring(@PathVariable Long examId, Model model) {
        Exam exam = examService.findById(examId);
        List<ExamQuestion> examQuestions = examQuestionService.findByExam(exam);
        int totalScore = examQuestions
                .stream()
                .map(examQuestion -> examQuestion.getScore())
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();

        model.addAttribute("exam", exam);
        model.addAttribute("examQuestions", examQuestions);
        model.addAttribute("totalScore", totalScore);
        return "exam-question-scores";
    }

    @PostMapping("/exam-question/update-score")
    public String updateSingleExamQuestionScore(
            @RequestParam Long examQuestionId,
            @RequestParam Integer score,
            @RequestParam Long examId) {

        ExamQuestion eq = examQuestionService.findById(examQuestionId);

        eq.setScore(score);
        examQuestionService.saveOrUpdate(eq);

        return "redirect:/teacher/course/exam/"+examId+"/set-scores";
    }


    //editing a question in questionBank
    @GetMapping("/course/questionsBank/{courseId}")
    public String showTheQuestionBank(@PathVariable Long courseId,Model model,Principal principal){
        User teacher = userService.findByUsername(principal.getName()).orElseThrow();
        Course course = courseService.findById(courseId);
        List<Question> questionByTeacherAndCourse = questionService.findByTeacherAndCourse(teacher, course);
        model.addAttribute("QuestionBank",questionByTeacherAndCourse);
        return "question-bank";
    }

    @GetMapping("/question/edit/{id}")
    public String goToEditQuestionPage(@PathVariable Long id,Model model){
        Question question = questionService.findById(id);
        model.addAttribute("question",question);
        return "edit-question";
    }

    @PostMapping("/question/edit/{id}")
    public String editQuestion(@PathVariable Long id,
                               @RequestParam String title,
                               @RequestParam String questionText){
        Question question = questionService.findById(id);
        question.setTitle(title);
        question.setQuestionText(questionText);
        questionService.saveOrUpdate(question);

        return "redirect:/teacher/question/edit/"+id;
    }

    //********************   Phase 4  **************************
    @GetMapping("/course/exam/results/{id}")
    public String showThePageToSeeResultsOfExam(@PathVariable Long id,Model model,Principal principal){
        User teacher= userService.findByUsername(principal.getName()).orElseThrow();

        Exam exam = examService.findById(id);
        if(!exam.getTeacher().getId().equals(teacher.getId())){
            throw new AccessDeniedException("you are not allowed to view this exam");
        }
        List<ExamAttempt> allExamAttempts = examAttemptService.findByExam(exam);
        model.addAttribute("exam",exam);
        model.addAttribute("allExamAttempts",allExamAttempts);
        return "show-all-exam-attempts";
    }

    @GetMapping("/exam/attempt/{attemptId}")
    public String seeTheQuestionOfAnExamAttempt(@PathVariable Long attemptId,Model model){


        ExamAttempt examAttempt = examAttemptService.findById(attemptId);

        List<McqAnswer> mcqAnswers =
                mcqAnswerService.findByExamAttempt(examAttempt);

        //the auto grading of MCQ
        for(McqAnswer answer:mcqAnswers){
            if(answer.getSelectedOption().isCorrect()){
                ExamQuestion examQuestion = examQuestionService.findById(answer.getExamQuestion().getId());
                answer.setScore(examQuestion.getScore());
                mcqAnswerService.saveOrUpdate(answer);
            }
        }



        //auto grading MCQ
        List<DescriptiveAnswer> descriptiveAnswers =
                descriptiveAnswerService.findByExamAttempt(examAttempt);

        model.addAttribute("examAttempt", examAttempt);
        model.addAttribute("mcqAnswers", mcqAnswers);
        model.addAttribute("descriptiveAnswers", descriptiveAnswers);

        return "teacher-grade-exam-attempt";

    }

    @PostMapping("/exam/attempt/{id}/grade")
    public String saveGrading(@PathVariable Long id,
                              @RequestParam Integer score,
                              @RequestParam Long answerIds){



        ExamAttempt examAttempt = examAttemptService.findById(id);

        DescriptiveAnswer answer = descriptiveAnswerService.findById(answerIds);
        answer.setScore(score);
        descriptiveAnswerService.saveOrUpdate(answer);
        return "redirect:/teacher/exam/attempt/"+examAttempt.getId();



    }

}

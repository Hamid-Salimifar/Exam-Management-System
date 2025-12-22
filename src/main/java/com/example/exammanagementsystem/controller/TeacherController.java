package com.example.exammanagementsystem.controller;

import com.example.exammanagementsystem.model.Course;
import com.example.exammanagementsystem.model.Exam;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.service.CourseService;
import com.example.exammanagementsystem.service.ExamService;
import com.example.exammanagementsystem.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final CourseService courseService;
    private final UserService userService;
    private final ExamService examService;

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

}

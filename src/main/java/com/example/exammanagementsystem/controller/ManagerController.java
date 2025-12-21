package com.example.exammanagementsystem.controller;

import com.example.exammanagementsystem.dto.user.UserResponseDto;
import com.example.exammanagementsystem.model.Course;
import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.service.CourseService;
import com.example.exammanagementsystem.service.RoleService;
import com.example.exammanagementsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final UserService userService;
    private final RoleService roleService;
    private final CourseService courseService;


    @GetMapping("all-user")
    public String allUser(Model model){
        List<UserResponseDto> allUserResponseDto = userService.seeAllUser();
        model.addAttribute("allUser",allUserResponseDto);
        return "all-user";
    }

    @GetMapping("/user/edit/{id}")
    public String editUser(@PathVariable Long id,Model model){

        User user = userService.findById(id);
        model.addAttribute("user",user);
        model.addAttribute("allRoles", roleService.findAll());

        return "edit-user";
    }

    @PostMapping("/user/edit")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam(required = false) List<Long> roleIds) {

        userService.editUser(user, roleIds);

        return "redirect:/manager/all-user";
    }


    @GetMapping("/search-page")
    public String goToSearchPage(){
        return "search";
    }

    @GetMapping("/search")
    public String searchPage(@RequestParam(required = false) String username,
                             @RequestParam(required = false) String firstname,
                             @RequestParam(required = false) String lastname,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) RoleName roleName,
                             Model model) {

        List<User> users = userService.searchUsers(username, firstname, lastname, email, roleName);
        List<UserResponseDto> allDto = users.stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getUsername(),
                        user.getFirstname(),
                        user.getLastname(),
                        user.getEmail(),
                        user.getRegisterStatus()
                ))
                .collect(Collectors.toList());
        model.addAttribute("allUser", allDto);
        return "all-user";

    }


    @GetMapping("/course/add")
    public String addCoursePage(Model model){
        model.addAttribute("Course",new Course());
        List<User> allTeacher = userService.findByRole(RoleName.ROLE_TEACHER);
        List<User> allStudent = userService.findByRole(RoleName.ROLE_STUDENT);
        model.addAttribute("allTeacher",allTeacher);
        model.addAttribute("allStudent",allStudent);

        return "add-course";
    }

    @PostMapping("/course/add")
    public String addCourse(@RequestParam String uniqueCode,
                            @RequestParam LocalDate startDate,
                            @RequestParam LocalDate endDate,
                            @RequestParam Long teacherId,
                            @RequestParam(required = false) List<Long> studentIds){


        courseService.createCourse(
                uniqueCode, startDate, endDate, teacherId, studentIds
        );

        return "redirect:/manager/courses";



    }

    @GetMapping("/courses")
    public String seeAllCourses(Model model){
        List<Course> allCourse = courseService.findAll();
        model.addAttribute("allCourse",allCourse);
        return "show-all-courses";
    }


    @GetMapping("/courses/edit/{id}")
    public String goToEditPage(@PathVariable Long id,Model model){
        Course course = courseService.findById(id);
        List<User> allTeacher = userService.findByRole(RoleName.ROLE_TEACHER);
        List<User> allStudent = userService.findByRole(RoleName.ROLE_STUDENT);
        model.addAttribute("course",course);
        model.addAttribute("teachers",allTeacher);
        model.addAttribute("students",allStudent);
        return "edit-course";
    }


    @PostMapping("/courses/edit")
    public String updateCourse(
            @RequestParam Long courseId,
            @RequestParam String uniqueCode,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam Long teacherId,
            @RequestParam(required = false) List<Long> studentIds
    ) {

        courseService.updateCourse(
                courseId,
                uniqueCode,
                startDate,
                endDate,
                teacherId,
                studentIds
        );

        return "redirect:/manager/courses";

    }


    @GetMapping("/courses/participants/{id}")
    public String showAllParticipantsOfCourse(@PathVariable Long id,Model model){
        Course course = courseService.findById(id);

        model.addAttribute("course",course);
        return "participants";
    }


    @PostMapping("/courses/delete")
    public String deleteCourse(@RequestParam Long courseId){
        courseService.deleteById(courseId);


        return "redirect:/manager/courses";

    }




}

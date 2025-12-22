package com.example.exammanagementsystem.service.impl;

import com.example.exammanagementsystem.model.Course;
import com.example.exammanagementsystem.model.Role;
import com.example.exammanagementsystem.model.RoleName;
import com.example.exammanagementsystem.model.User;
import com.example.exammanagementsystem.repository.CourseRepository;
import com.example.exammanagementsystem.service.CourseService;
import com.example.exammanagementsystem.service.RoleService;
import com.example.exammanagementsystem.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseServiceImpl extends BaseServiceImpl<Course> implements CourseService {

    private final UserService userService;
    private final CourseRepository courseRepository;


    public CourseServiceImpl(CourseRepository courseRepository, UserService userService) {
        super(courseRepository);
        this.userService = userService;
        this.courseRepository = courseRepository;

    }

    @Override
    public List<Course> findByTeacher(User teacher) {
        return courseRepository.findByTeacher(teacher);
    }

    @Override
    public Course updateCourse(Long courseId, String uniqueCode, LocalDate startDate, LocalDate endDate, Long teacherId, List<Long> studentIds) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalStateException("Course not found"));


        course.setUniqueCode(uniqueCode);
        course.setStartDate(startDate);
        course.setEndDate(endDate);


        User teacher = userService.findById(teacherId);

        boolean isTeacher = teacher.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleName.ROLE_TEACHER);

        if (!isTeacher) {
            throw new IllegalStateException("Selected user is not a teacher");
        }

        course.setTeacher(teacher);


        Set<User> students = new HashSet<>();

        if (studentIds != null) {
            for (Long studentId : studentIds) {
                User student = userService.findById(studentId);
                students.add(student);
            }
        }

        course.setStudent(students);


        return courseRepository.save(course);
    }

    @Override
    public Course createCourse(String uniqueCode, LocalDate startDate, LocalDate endDate, Long teacherId, List<Long> studentIds) {
        User teacher = userService.findById(teacherId);

        boolean isTeacher = teacher.getRoles().stream()
                .anyMatch(role -> role.getName() == RoleName.ROLE_TEACHER);

        if (!isTeacher) {
            throw new IllegalStateException("Selected user is not a teacher");
        }


        Set<User> students = new HashSet<>();
        for (Long id : studentIds) {
            User user = userService.findById(id);
            boolean isStudent = user.getRoles().stream()
                    .anyMatch(role -> role.getName() == RoleName.ROLE_STUDENT);

            if (!isStudent) {
                throw new IllegalStateException(
                        "User with id " + id + " is not a student"
                );
            }
            students.add(user);
        }


        Course course = Course.builder()
                .uniqueCode(uniqueCode)
                .startDate(startDate)
                .endDate(endDate)
                .teacher(teacher)
                .student(students)
                .build();


        return courseRepository.save(course);
    }

}
